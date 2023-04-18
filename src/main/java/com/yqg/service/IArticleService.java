package com.yqg.service;

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

}
