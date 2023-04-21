package com.yqg.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@TableName("user")
@NoArgsConstructor
public class User{
    String userId;
    String userName;
    String userAvatarUrl;
    String userDes;
    Integer userExp;
    String userTags;
    @JsonIgnore
    String password;
    Integer status;
    String email;
    String roleId;
    String createTime;

    public User(String userId, String userName, String userAvatarUrl, String userDes, Integer userExp, String userTags, String password, Integer status, String email, String roleId, String createTime, Integer loginTimes, Integer integral) {
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
        this.userDes = userDes;
        this.userExp = userExp;
        this.userTags = userTags;
        this.password = password;
        this.status = status;
        this.email = email;
        this.roleId = roleId;
        this.createTime = createTime;
        this.loginTimes = loginTimes;
        this.integral = integral;
    }

    Integer loginTimes;
    Integer integral;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
    }

    public void setUserExp(Integer userExp) {
        this.userExp = userExp;
    }

    public void setUserTags(String userTags) {
        this.userTags = userTags;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public String getUserDes() {
        return userDes;
    }

    public Integer getUserExp() {
        return userExp;
    }

    public String getUserTags() {
        return userTags;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public Integer getIntegral() {
        return integral;
    }
}
