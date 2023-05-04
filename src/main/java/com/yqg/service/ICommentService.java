package com.yqg.service;

import com.yqg.vo.ActionComment;
import com.yqg.vo.Comment;
import com.yqg.vo.Topic;

import java.util.List;

/**
 * @author Administrator
 */
public interface ICommentService {
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

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    int deleteComment(String commentId);
}
