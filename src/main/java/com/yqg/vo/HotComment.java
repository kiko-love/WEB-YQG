package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotComment {
    private String commentId;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private String topic;
    private Double hot;
}
