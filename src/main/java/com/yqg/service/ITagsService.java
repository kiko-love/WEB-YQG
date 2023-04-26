package com.yqg.service;

import com.yqg.vo.Tags;
import com.yqg.vo.Topic;

import java.util.List;

/**
 * @author KIKO
 */
public interface ITagsService {
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
