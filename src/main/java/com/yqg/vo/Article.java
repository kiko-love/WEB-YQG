package com.yqg.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private String articleId;
    private String title;
    private String summary;
    @JsonIgnore
    private String content;
    private String coverImage;
    private Integer likeCount;
    private Integer readCount;
    private Integer commentCount;
    private String tags;
    private String userId;
    private String createTime;
    private String updateTime;
    private Integer audit;

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setContent(String content) {
        this.content = content;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public String getTags() {
        return tags;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Integer getAudit() {
        return audit;
    }
}
