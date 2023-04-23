package com.yqg.controller;

import com.yqg.service.impl.ArticleServiceImpl;
import com.yqg.service.impl.UserArticleOperationServiceImpl;
import com.yqg.vo.Article;
import jakarta.annotation.Resource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.*;

/**
 * @author KIKO
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private UserArticleOperationServiceImpl userArticleOperationService;
    @Resource
    private ArticleServiceImpl articleService;

    @RequestMapping("/list/{userId}/{type}")
    public String recommendArticles(@PathVariable("userId") String userId,
                                    @PathVariable("type") String type) throws TasteException {
        return userArticleOperationService.genRecArticlesIntegration(userId, 10);
    }

    @RequestMapping("/list/more")
    public String getMoreArticles() throws TasteException {
        return userArticleOperationService.genRecArticlesIntegration(null,10);
    }

    @RequestMapping("/content/{articleId}")
    public String getArticleContent(@PathVariable("articleId") String articleId) {
        return articleService.getArticleContent(articleId);
    }

    @PostMapping("/add")
    public String addNewArticle(@RequestBody Article article) {
        return articleService.addNewArticle(article);
    }

    @PostMapping("/updateAudit")
    public String updateAudit(@RequestParam("articleId") String articleId,
                              @RequestParam("audit") Integer audit) {
        return articleService.updateArticleAudit(articleId, audit);
    }

}
