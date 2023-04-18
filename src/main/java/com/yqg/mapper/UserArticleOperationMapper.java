package com.yqg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yqg.vo.UserArticleOperation;
import org.apache.ibatis.annotations.Mapper;

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
}
