package com.yqg.mapper;

import com.yqg.vo.ActionComment;
import com.yqg.vo.Comment;
import com.yqg.vo.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface CommentMapper {
    /**
     * 发表评论
     * @param comment
     * @return
     */
    int addComment(Comment comment);

    /**
     * 获取话题列表
     * @return
     */
    List<Topic> getTopicList();

    /**
     * 获取评论列表
     * @return
     */
    List<ActionComment> getCommentList();
}
