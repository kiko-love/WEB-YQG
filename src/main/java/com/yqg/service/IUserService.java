package com.yqg.service;

import com.yqg.vo.User;

import java.util.List;

/**
 * @author KIKO
 */
public interface IUserService {

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();

    /**
     * 通过用户名获取用户信息（此处用户名可以同时为userID，userName。email）
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * 检测用户账号状态
     *
     * @param userId
     * @return
     */
    Long checkUserStatus(String userId);

    /**
     * 更新用户登录次数
     *
     * @param user
     * @return
     */
    int updateUserLoginTimes(User user);
}
