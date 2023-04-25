package com.yqg.controller;

import com.yqg.service.impl.ResourceServicesImpl;
import com.yqg.vo.UploadResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author KIKO
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceServicesImpl resourceServices;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             HttpServletRequest request,
                             @ModelAttribute UploadResource uploadResource) throws IOException, NoSuchAlgorithmException {
        return resourceServices.uploadFile(file, uploadResource, request);
    }

    @GetMapping("/download/{userId}/{fileId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String userId,
                                                            @PathVariable String fileId) throws IOException {
        return resourceServices.downloadFile(userId, fileId);
    }


    @GetMapping("/list")
    public String getAllResources() {
        return resourceServices.getAllResources();
    }

    @GetMapping("/list/{tag}")
    public String getResourceByTag(@PathVariable String tag) {
        return resourceServices.getResourcesByTags(tag);
    }

    @GetMapping("/hot")
    public String getHotResources() {
        return resourceServices.getHot();
    }
}
