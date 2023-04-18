package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author KIKO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private Integer id;
    private String fromId;
    private String toId;
    private String title;
    private String type;
    private String content;
    private Integer isRead;
    private Integer sendTime;
    private String createTime;
    private String readTime;
}
