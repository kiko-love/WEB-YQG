package com.yqg.mapper;

import com.yqg.vo.Tags;
import com.yqg.vo.Topic;
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

    /**
     * 获取话题列表
     * @return
     */

    List<Topic> getTopic();
}
