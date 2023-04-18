package com.yqg.mapper;

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
}
