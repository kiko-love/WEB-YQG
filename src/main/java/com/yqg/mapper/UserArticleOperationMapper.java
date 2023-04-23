package com.yqg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yqg.vo.Article;
import com.yqg.vo.UserArticleOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KIKO
 */
@Mapper
public interface UserArticleOperationMapper extends BaseMapper<UserArticleOperation> {
    /**
     * 获取所有用户的行为记录
     *
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
