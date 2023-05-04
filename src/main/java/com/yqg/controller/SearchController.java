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
    private ResourceServicesImpl resourceServices;

    @GetMapping("/article/{keyword}/{pageNum}/{pageSize}")
    public String searchArticle(@PathVariable("keyword") String keyword,
                                @PathVariable("pageNum") int pageNum,
                                @PathVariable("pageSize") int pageSize) {
        return articleService.searchArticle(keyword, pageNum, pageSize);
    }

    @GetMapping("/user/{keyword}/{pageNum}/{pageSize}")
    public String searchUser(@PathVariable("keyword") String keyword,
                             @PathVariable("pageNum") int pageNum,
                             @PathVariable("pageSize") int pageSize) {
        return userService.searchUserList(keyword, pageNum, pageSize);
    }

    @GetMapping("/resource/{keyword}/{pageNum}/{pageSize}")
    public String searchResource(@PathVariable("keyword") String keyword,
                                 @PathVariable("pageNum") int pageNum,
                                 @PathVariable("pageSize") int pageSize) {
        return resourceServices.getResByKey(keyword, pageNum, pageSize);
    }

}
