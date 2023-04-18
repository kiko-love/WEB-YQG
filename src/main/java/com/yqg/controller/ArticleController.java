package com.yqg.controller;

import com.yqg.service.impl.UserArticleOperationServiceImpl;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KIKO
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private UserArticleOperationServiceImpl userArticleOperationService;

    @ResponseBody
    @RequestMapping("/list/{userId}")
    public String recommendArticles(@PathVariable("userId") String userId) throws TasteException {
        return userArticleOperationService.genRecArticlesIntegration(userId,10);
    }

}
