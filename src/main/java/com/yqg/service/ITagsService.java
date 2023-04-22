package com.yqg.service;

import com.yqg.vo.Tags;

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
}
