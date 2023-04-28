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

    /**
     * 分页查询文章列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<RecommendArticle> getArticleList(Integer pageNum, Integer pageSize);

    /**
     * 根据审核状态获取文章列表
     * @param audit
     * @return
     */
    List<RecommendArticle> getArticleListByAudit(Integer audit);

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    int deleteArticle(String articleId);

    /**
     * 根据关键字获取文章列表
     * @param key
     * @return
     */
    List<RecommendArticle> getListByKey(String key);

}
