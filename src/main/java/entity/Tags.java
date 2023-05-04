package entity;

import java.io.Serializable;

/**
 * (Tags)实体类
 *
 * @author makejava
 * @since 2023-04-22 19:34:54
 */
public class Tags implements Serializable {
    private static final long serialVersionUID = 530289438110787140L;
    /**
     * 标签id
     */
    private String tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签描述
     */
    private String tagDescription;
    /**
     * 标签使用次数
     */
    private Long postCount;
    /**
     * 标签创建者ID
     */
    private String userId;
    /**
     * 创建日期
     */
    private Long createTime;


    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}

