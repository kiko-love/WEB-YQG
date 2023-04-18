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
public class Article {
    private String articleId;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private Integer lickCount;
    private Integer readCount;
    private Integer commentCount;
    private String tags;
    private String userId;
    private String createTime;
    private String updateTime;
    private Integer audit;

}
