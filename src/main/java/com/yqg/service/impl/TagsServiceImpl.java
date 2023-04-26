package com.yqg.service.impl;

import com.yqg.R.Result;
import com.yqg.mapper.TagsMapper;
import com.yqg.service.ITagsService;
import com.yqg.vo.Tags;
import com.yqg.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KIKO
 */
@Service
public class TagsServiceImpl implements ITagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<Tags> getTags() {
        return tagsMapper.getTags();
    }

    @Override
    public List<Topic> getTopic() {
        return tagsMapper.getTopic();
    }

    public String getAllTopic(){
        if (tagsMapper.getTopic().size() > 0) {
            return Result.success(tagsMapper.getTopic());
        }
        return Result.error("获取话题列表失败");
    }

    public String getTagsList() {
        if (tagsMapper.getTags().size() > 0) {
            return Result.success(tagsMapper.getTags());
        }
        return Result.error("获取标签列表失败");
    }
}
