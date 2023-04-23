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
public class UploadResource {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String realName;
    private String fileType;
    private String tags;
    /**
     * 付费类型（0=免费，1=积分）
     */
    private Integer fee;
    private Integer feeCost;
    /**
     * 资源状态（0审核中、1已发布、-1已下架等）
     */
    private Integer status;

    private Integer viewCount;
    private Integer downloadCount;
    private Integer likeCount;
    private String createTime;
    private String updateTime;
}
