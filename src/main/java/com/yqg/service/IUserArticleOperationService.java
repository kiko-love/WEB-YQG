package com.yqg.service;

import com.yqg.vo.Article;
import com.yqg.vo.UserArticleOperation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KIKO
 */
public interface IUserArticleOperationService {
    /**
     * 获取所有用户的行为记录
     * @return
     */
    List<UserArticleOperation> getAllUserPreference();
    /**
     * 获取指定数量的文章ID列表
     * @param num
     * @return
     */
    List<Long> getRandomListIdsByNum(@Param("num") int num);

    /**
     * 获取指定数量的文章
     * @param num
     * @return
     */
    List<Article> getRandomListByNum(@Param("num") int num);
}
