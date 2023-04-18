package com.yqg.controller;

import com.yqg.service.impl.ResourceServicesImpl;
import com.yqg.vo.UploadResource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author KIKO
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceServicesImpl resourceServices;
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             HttpServletRequest request,
                             @ModelAttribute UploadResource uploadResource) throws IOException, NoSuchAlgorithmException {
        return resourceServices.uploadFile(file, uploadResource,request);
    }
}
