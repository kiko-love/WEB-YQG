package com.yqg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.utils.FileTypeUtils;
import com.yqg.vo.UploadResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
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
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author KIKO
 */
@Service
public class ResourceServicesImpl {
    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${file.upload-dir}")
    private String uploadDir;
    public String uploadFile(MultipartFile file, UploadResource uploadResource, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        // 获取文件类型和用户ID
        String fileType = getFileType(file.getOriginalFilename());
        String userName = uploadResource.getUserId();
        // 构建保存路径和文件名
        String basePath = uploadDir+ File.separator  + fileType + File.separator + userName;
        String fileName = IdUtil.simpleUUID() + getFileSuffix(Objects.requireNonNull(file.getOriginalFilename()));
        String savePath = basePath + File.separator + fileName;
        String relativePath = fileType + File.separator + userName + File.separator + fileName;
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
        // 返回外部映射链接
        String url = getResourceUrl(relativePath,request);
        HashMap<Object, Object> hashMap = new HashMap<>(16);
        hashMap.put("url", url);
        return Result.success(hashMap);
    }

    /**
     * 根据文件路径获取外部映射链接
     * @param filePath
     * @return
     * @throws IOException
     */
    private String getResourceUrl(String filePath,HttpServletRequest request) throws IOException {
        //协议 :// ip地址 ：端口号 / 文件目录(/images/2020/03/15/xxx.jpg)
        String fileUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort()+ "/"
                + filePath;
        fileUrl = fileUrl.replaceAll("\\\\", "/");
        return fileUrl;
    }

    /**
     * 根据文件名获取文件后缀
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
}
