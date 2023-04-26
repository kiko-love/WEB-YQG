package com.yqg.service;

import com.yqg.vo.Article;
import com.yqg.vo.RecommendArticle;

import java.util.List;

/**
 * @author KIKO
 */
public interface IArticleService {
    /**
     * 获取指定id的文章（批量）
     *
     * @param articleIds
     * @return
     */
    List<RecommendArticle> getArticles(List<Long> articleIds);

    /**
     * 获取随机文章
     *
     * @param num
     * @return
     */
    List<RecommendArticle> getRandomArticle(Integer num);
    /**
     * 获取文章列表
     * @param article
     * @return
     */
    int addArticle(Article article);

    /**
     * 更新文章审核状态
     *
     * @param articleId
     * @param audit
     * @return
     */
    int updateAudit(String articleId, Integer audit);

    /**
     * 获取最新num条文章记录
     * @param num
     * @return
     */
    List<RecommendArticle> getLastArticles(int num);
    /**
     * 文章阅读量+1
     * @param articleId
     * @return
     */
    int incrementReadCount(String articleId);

    /**
     * 更新文章
     *
     * @param article
     * @return
     */
    int updateArticle(Article article);

}
