package com.yqg.service.impl;

import com.yqg.mapper.ArticleMapper;
import com.yqg.service.IArticleService;
import com.yqg.vo.RecommendArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KIKO
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<RecommendArticle> getArticles(List<Long> articleIds) {
        return articleMapper.getLikeArticle(articleIds);
    }

    @Override
    public List<RecommendArticle> getRandomArticle(Integer num) {
        return articleMapper.getRandomArticle(num);
    }
}
