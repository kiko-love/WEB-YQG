package com.yqg.controller;


import com.yqg.service.impl.CommentServiceImpl;
import com.yqg.vo.Comment;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentServiceImpl commentService;

    @PostMapping("/add")
    public String addComment(@RequestBody Comment comment) {
        return commentService.addNewComment(comment);
    }

    @GetMapping("/list")
    public String getCommentList() {
        return commentService.getAllComments();
    }

    @DeleteMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") String commentId) {
        return commentService.deleteMyComment(commentId);
    }

    @PostMapping("/linkInfo")
    public String getLinkInfo(@RequestParam("url") String url) throws IOException {
        return commentService.getUrlInfo(url);
    }
}
