package com.yqg.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.yqg.R.Result;
import com.yqg.mapper.CommentMapper;
import com.yqg.service.ICommentService;
import com.yqg.vo.ActionComment;
import com.yqg.vo.Comment;
import com.yqg.vo.Topic;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    public String addNewComment(Comment comment) {

        comment.setCommentId(String.valueOf(IdUtil
                .getSnowflake(1, 1).nextId()));
        comment.setCreateTime(String.valueOf(DateUtil.current()));
        comment.setIsDeleted(0);
        comment.setCommentCount(0);
        comment.setLikeCount(0);
        int i = commentMapper.addComment(comment);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error("评论失败");
        }

    }

    public String getAllComments() {
        List<ActionComment> commentList = commentMapper.getCommentList();
        if (commentList == null || commentList.size() == 0) {
            return Result.error("暂无评论");
        }
        return Result.success(commentList);
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public List<Topic> getTopicList() {
        return commentMapper.getTopicList();
    }

    @Override
    public List<ActionComment> getCommentList() {
        return commentMapper.getCommentList();
    }
}
