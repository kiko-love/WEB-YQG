package com.yqg.controller;


import com.yqg.service.impl.TagsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KIKO
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsServiceImpl tagsService;

    @RequestMapping("/list")
    public String getTagsList() {
        return tagsService.getTagsList();
    }
}
