package com.yqg.mapper;

import com.yqg.vo.Tags;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author KIKO
 */
@Mapper
public interface TagsMapper {
    /**
     * 获取标签列表
     *
     * @return
     */
    List<Tags> getTags();
}
