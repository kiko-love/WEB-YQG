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
public class Topic {
    private Integer id;
    private String title;
    private String content;
    private String userId;
    private String createTime;
}
