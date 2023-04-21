package com.yqg.service;

import com.yqg.vo.User;

import java.util.List;

/**
 * @author KIKO
 */
public interface IUserService {


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
    /**
     * 获取所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 通过用户id删除用户
     * @param userId
     * @return
     */
    int deleteUserById(String userId);

}