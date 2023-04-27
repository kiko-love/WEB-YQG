package com.yqg.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yqg.R.Result;
import com.yqg.R.ResultEnum;
import com.yqg.mapper.ArticleMapper;
import com.yqg.service.IArticleService;
import com.yqg.vo.Article;
import com.yqg.vo.DetailArticle;
import com.yqg.vo.RecommendArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<RecommendArticle> getLastArticles(int num) {
        return articleMapper.getLastArticles(num);
    }

    @Override
    public int incrementReadCount(String articleId) {
        return articleMapper.incrementReadCount(articleId);
    }

    @Override
    public int updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    public List<RecommendArticle> getArticleList(Integer pageNum, Integer pageSize) {
        return articleMapper.getArticleList(pageNum, pageSize);
    }


    public String getPageArticleList(Integer pageNum, Integer pageSize,String orderBy,String orderType) {
        //只允许指定的字段排序，防止sql注入
        String[] orderByArr = {"create_time", "update_time","audit","read_count","like_count","comment_count","audit"};
        String orderByStr = "";
        if (StringUtils.isNotEmpty(orderBy) && Arrays.asList(orderByArr).contains(orderBy.toLowerCase())) {
            orderByStr = String.format("%s %s", orderBy.toLowerCase(), "asc".equalsIgnoreCase(orderType) ? "asc" : "desc");
        } else {
            // 默认排序
            orderByStr = "";
        }
        PageHelper.startPage(pageNum, pageSize,orderByStr);
        List<RecommendArticle> articleList = articleMapper.getArticleList(pageNum, pageSize);
        PageInfo<RecommendArticle> pageInfo = new PageInfo<>(articleList);
        if (articleList.size() > 0) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("total", pageInfo.getTotal());
            map.put("list", articleList);
            return Result.success(map);
        }
        return Result.error("文章列表为空");
    }

    public String updateOldArticle(Article article) {
        if (article != null) {
            if (article.getArticleId() == null || "".equals(article.getArticleId())) {
                return Result.error("文章id为空");
            }
            article.setUpdateTime(String.valueOf(DateUtil.current()));
            article.setAudit(0);
        } else {
            return Result.error("文章参数为空");
        }
        if (articleMapper.updateArticle(article) > 0) {
            return Result.success("文章更新成功");
        }
        return Result.error("文章更新失败");
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
            article.setUpdateTime(article.getCreateTime());
            article.setAudit(0);
            article.setLikeCount(0);
            article.setReadCount(0);
            article.setCommentCount(0);
            article.setCreateTime(String.valueOf(DateUtil.current()));
            article.setUpdateTime(article.getCreateTime());
        } else {
            return Result.error("文章参数为空");
        }
        if (articleMapper.addArticle(article) > 0) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("articleId", article.getArticleId());
            return Result.success(map);
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
        if (article == null || article.getAudit() == 110 || article.getAudit() == -1) {
            return Result.error("未找到该文章");
        }
        if (article.getAudit() == 0) {
            Result<Object> result = new Result<>();
            result.setCode(ResultEnum.ARTICLE_IS_AUDITING.getCode());
            result.setMsg(ResultEnum.ARTICLE_IS_AUDITING.getMsg());
            return JSONObject.toJSONString(result);
        }
        incrementReadCount(articleId);
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
            article.setTagsArray(null);
        }
        return article;
    }
}
