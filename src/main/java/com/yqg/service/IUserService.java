package com.yqg.service;

import com.yqg.vo.HotUser;
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

    /**
     * 获取热门用户列表
     * @return
     */
    List<HotUser> getHotUser();

    /**
     * 通过关键字获取用户
     * @param keyword
     * @return
     */
    List<User> getUserByKeyword(String keyword);

}
