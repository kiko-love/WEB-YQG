package com.yqg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.mapper.ArticleMapper;
import com.yqg.service.IArticleService;
import com.yqg.vo.Article;
import com.yqg.vo.DetailArticle;
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

    @Override
    public int addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    public int updateAudit(String articleId, Integer audit) {
        return articleMapper.updateAudit(articleId, audit);
    }


    /**
     * 文章上传
     *
     * @param article
     * @return
     */
    public String addNewArticle(Article article) {
        if (article != null) {
            article.setArticleId(String.valueOf(IdUtil
                    .getSnowflake(1, 1).nextId()));
            article.setCoverImage("https://vitejs.dev/logo-with-shadow.png");
            String summary = article.getContent().substring(0, 100);
            article.setUpdateTime(article.getCreateTime());
            article.setAudit(0);
            article.setLikeCount(0);
            article.setReadCount(0);
            article.setCommentCount(0);
        } else {
            return Result.error("文章参数为空");
        }
        if (articleMapper.addArticle(article) > 0) {
            return Result.success("文章上传成功");
        }
        return Result.error("文章上传失败");
    }

    /**
     * 更新文章审核状态
     *
     * @param articleId
     * @param audit
     * @return
     */
    public String updateArticleAudit(String articleId, Integer audit) {
        if (articleMapper.updateAudit(articleId, audit) > 0) {
            return Result.success("文章审核状态更新成功");
        }
        return Result.error("文章审核状态更新失败");
    }

    /**
     * 获取文章内容
     *
     * @param articleId
     * @return
     */
    public String getArticleContent(String articleId) {
        DetailArticle article = articleMapper.getArticleContent(articleId);
        if (article == null) {
            return Result.error("未找到该文章");
        }
        article.setDetailContent(article.getContent());
        return Result.success(convertTagsStringToList(article));
    }

    /**
     * 转换Tags字符串为数组
     *
     * @param article
     * @return
     */
    public static DetailArticle convertTagsStringToList(DetailArticle article) {
        if (article.getTags() != null) {
            // 将 tags 字符串转换成数组，并赋值给 RecommendArticle 对象的 tags 属性
            article.setTagsArray(article.getTags().split(","));
        } else {
            // 如果 tags 字段为空，则将 tags 属性设置为一个空数组
            article.setTagsArray(new String[0]);
        }
        return article;
    }
}
