package com.yqg.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.mapper.ResourceMapper;
import com.yqg.service.IResourceService;
import com.yqg.utils.FileTypeUtils;
import com.yqg.vo.ActionResource;
import com.yqg.vo.DetailArticle;
import com.yqg.vo.UploadResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author KIKO
 */
@Service
public class ResourceServicesImpl implements IResourceService {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ResourceMapper resourceMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    public String getAllResources() {
        List<ActionResource> list = getResourcesList();
        if (list == null || list.size() == 0) {
            return Result.success("no resource");
        } else {
            list = convertTagsStringToList(list);
        }
        //随机排列list元素
        list = list.stream().sorted((a, b) -> Math.random() > 0.5 ? 1 : -1)
                .collect(Collectors.toList());
        return Result.success(list);
    }

    public String getHot() {
        List<UploadResource> list = getHotResource();
        if (list == null || list.size() == 0) {
            return Result.success(null);
        } else {
            return Result.success(list);
        }

    }

    /**
     * 根据标签获取资源列表
     *
     * @param tag
     * @return
     */
    public String getResourcesByTags(String tag) {
        List<ActionResource> list = null;
        if ("all".equals(tag)) {
            list = getResourcesList();
        } else {
            list = getResourceByTag(tag);
        }
        if (list == null || list.size() == 0) {
            return Result.success(null);
        } else {
            list = convertTagsStringToList(list);
        }
        return Result.success(list);
    }

    /**
     * 转换Tags字符串为数组
     *
     * @param resource
     * @return
     */
    public static List<ActionResource> convertTagsStringToList(List<ActionResource> resource) {
        for (ActionResource actionResource : resource) {
            String tags = actionResource.getTags();
            if (tags != null && !tags.isEmpty()) {
                String[] tagsArray = tags.split(",");
                actionResource.setTagsArray(tagsArray);
            }
        }
        return resource;
    }

    public String uploadFile(MultipartFile file, UploadResource uploadResource, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        // 获取文件类型和用户ID
        String fileType = getFileType(file.getOriginalFilename());
        String userId = uploadResource.getUserId();
        // 构建保存路径和文件名
        String basePath = uploadDir + File.separator + fileType + File.separator + userId;
        String fileId = IdUtil.simpleUUID();
        String fileName = fileId + getFileSuffix(Objects.requireNonNull(file.getOriginalFilename()));
        String savePath = basePath + File.separator + fileName;
        String relativePath = fileType + File.separator + userId + File.separator + fileName;
        basePath = basePath.replace("/", File.separator);
        basePath = URLDecoder.decode(basePath, StandardCharsets.UTF_8);
        savePath = savePath.replace("/", File.separator);
        savePath = URLDecoder.decode(savePath, StandardCharsets.UTF_8);
        // 创建保存目录
        File dir = new File(basePath);
        if (!dir.exists()) {
            boolean result = dir.mkdirs();
            if (!result) {
                throw new IOException("创建保存目录失败");
            }
        }
        // 保存文件到本地磁盘或者云存储
        File saveFile = new File(savePath);
        file.transferTo(saveFile);
        List<UploadResource> list = new ArrayList<>();
        uploadResource.setId(fileId);
        uploadResource.setFee(0);
        uploadResource.setFileType(fileType);
        uploadResource.setRealName(file.getOriginalFilename());
        uploadResource.setStatus(0);
        uploadResource.setCreateTime(String.valueOf(DateUtil.current()));
        uploadResource.setDownloadCount(0);
        uploadResource.setUpdateTime(uploadResource.getCreateTime());
        uploadResource.setLikeCount(0);
        uploadResource.setFeeCost(0);
        uploadResource.setViewCount(0);
        list.add(uploadResource);
        int i = insertBatchResources(list);
        if (i == 0) {
            return Result.error("上传失败");
        }
        // 返回外部映射链接
        String url = getResourceUrl(relativePath, request);
        HashMap<Object, Object> hashMap = new HashMap<>(16);
        hashMap.put("url", url);
        return Result.success(hashMap);
    }

    public ResponseEntity<InputStreamResource> downloadFile(String userId, String fileId) throws IOException {
        UploadResource uploadResource = resourceMapper.getResourcesById(fileId);
        if (uploadResource == null) {
            return ResponseEntity.notFound().build();
        }
        String fileType = uploadResource.getFileType();
        String basePath = uploadDir + File.separator + fileType + File.separator + userId;
        String fileName = fileId + getFileSuffix(Objects.requireNonNull(uploadResource.getRealName()));
        String savePath = basePath + File.separator + fileName;
        FileSystemResource file = new FileSystemResource(savePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * 根据文件路径获取外部映射链接
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private String getResourceUrl(String filePath, HttpServletRequest request) throws IOException {
        //协议 :// ip地址 ：端口号 / 文件目录(/images/2020/03/15/xxx.jpg)
        String fileUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + "/"
                + filePath;
        fileUrl = fileUrl.replaceAll("\\\\", "/");
        return fileUrl;
    }


    /**
     * 根据文件名获取文件后缀
     *
     * @param filename
     * @return
     */
    private String getFileSuffix(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return filename.substring(dotIndex);
    }

    /**
     * 根据文件名获取文件类型
     *
     * @param filename
     * @return
     */
    private String getFileType(String filename) {
        return FileTypeUtils.getFileType(filename);
    }

    /**
     * 判断文件是否已经存在
     *
     * @param filename 要判断的文件名（可以是哈希值或校验和）
     * @return 如果文件已经存在则返回true，否则返回false
     */
    private boolean isFileExist(String filename) {
        Path path = Paths.get(uploadDir + "/" + filename);
        return Files.exists(path);
    }

    /**
     * 计算文件的哈希值
     *
     * @param inputStream 文件输入流
     * @return 文件的哈希值（以16进制字符串表示）
     */
    private String calculateHash(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        byte[] hash = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 计算文件的校验和
     *
     * @param inputStream 文件输入流
     * @return 文件的校验和
     */
    private long calculateChecksum(InputStream inputStream) throws IOException {
        Checksum checksum = new CRC32();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            checksum.update(buffer, 0, bytesRead);
        }
        return checksum.getValue();
    }

    @Override
    public List<UploadResource> getUploadResource(List<Long> resourceIds) {
        return resourceMapper.getUploadResource(resourceIds);
    }

    @Override
    public int insertBatchResources(List<UploadResource> list) {
        return resourceMapper.insertBatchResources(list);
    }

    @Override
    public UploadResource getResourcesById(String fileId) {
        return resourceMapper.getResourcesById(fileId);
    }

    @Override
    public List<ActionResource> getResourcesList() {
        return resourceMapper.getResourcesList();
    }

    @Override
    public List<ActionResource> getResourceByTag(String tag) {
        return resourceMapper.getResourceByTag(tag);
    }


    @Override
    public List<UploadResource> getHotResource() {
        return resourceMapper.getHotResource();
    }
}
