package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZYY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotUser {
    private Integer userId;
    private String userName;
    private String userAvatarUrl;
    private String userDes;
    private Integer userLevel;
    private Integer articleCount;
    private Integer likeCountSum;
    private Integer readCountSum;
    private Integer commentCountSum;
    private Double hotScore;
}
