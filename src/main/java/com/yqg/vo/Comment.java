package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentId;
    private String articleId;
    private String link;
    private Integer commentCount;
    private Integer likeCount;
    private String topic;
    private String userId;
    private String content;
    private String parentId;
    private String createTime;
    private Integer isDeleted;
}
