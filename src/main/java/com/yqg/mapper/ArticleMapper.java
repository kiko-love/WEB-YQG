package com.yqg.mapper;

import com.yqg.vo.Article;
import com.yqg.vo.DetailArticle;
import com.yqg.vo.RecommendArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author KIKO
 */
@Mapper
public interface ArticleMapper {
    /**
     * 获取指定id的文章（批量）
     * @param articleIds
     * @return 文章列表
     */
    List<RecommendArticle> getLikeArticle(List<Long> articleIds);

    /**
     * 获取随机文章
     * @param num
     * @return 文章列表
     */
    List<RecommendArticle> getRandomArticle(Integer num);

    /**
     * 获取文章内容
     *
     * @param articleId
     * @return
     */
    DetailArticle getArticleContent(String articleId);

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
}
