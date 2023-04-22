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
public class Tags {
    String tagId;
    String tagName;
    String tagDescription;
    String userId;
    Integer postCount;
    String createTime;
}
