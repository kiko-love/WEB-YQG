package com.yqg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yqg.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author KIKO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户名获取用户
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * 添加新用户
     * @param user
     * @return
     */
    int addUserOne(User user);

    /**
     * 更新用户状态（可用于激活或者封禁账号 ）
     *
     * @param status
     * @param userId
     * @return
     */
    int updateUserStatus(@Param("status") Integer status,
                         @Param("userId")String userId);

    /**
     * 检测用户账号状态
     *
     * @param userId
     * @return
     */
    Long checkUserStatus(@Param("userId") String userId);

    /**
     * 更新用户登录次数
     *
     * @param user
     * @return
     */
    int updateUserLoginTimes(User user);

}
