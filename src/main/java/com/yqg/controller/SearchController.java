package com.yqg.controller;

import com.yqg.service.impl.ArticleServiceImpl;
import com.yqg.service.impl.ResourceServicesImpl;
import com.yqg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ResourceServicesImpl recommendService;

    @GetMapping("/article/{keyword}")
    public String searchArticle(@PathVariable("keyword") String keyword) {
        return articleService.searchArticle(keyword);
    }

    @GetMapping("/user/{keyword}")
    public String searchUser(@PathVariable("keyword") String keyword) {
        return userService.searchUserList(keyword);
    }

}
