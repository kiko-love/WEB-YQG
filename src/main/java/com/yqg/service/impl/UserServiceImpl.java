package com.yqg.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.yqg.R.NomalEnum;
import com.yqg.R.Result;
import com.yqg.R.ResultEnum;
import com.yqg.mapper.UserMapper;
import com.yqg.service.IEmailService;
import com.yqg.service.IUserService;
import com.yqg.utils.RedisUtils;
import com.yqg.utils.UniqueIdGeneratorUtils;
import com.yqg.vo.User;
import com.yqg.vo.UserBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KIKO
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private static final Long REGISTER_EXPIRE = 5 * 60L;
    private static final Long LOGIN_EXPIRE = 30 * 60L;

    @Autowired
    private IEmailService iEmailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;


    public String adminDeleteUser(String userId) {
        int i = userMapper.deleteUser(userId);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }

    public String adminAddUser(User user) {
        User userByAccount = userMapper.getUserByAccount(user.getUserId());
        if (userByAccount != null) {
            return Result.error("账号已存在");
        }
        user.setUserId(String.valueOf(IdUtil.getSnowflake(1, 1).nextId()));
        user.setCreateTime(String.valueOf(DateUtil.current()));
        user.setUserExp(0);
        user.setIntegral(0);
        user.setLoginTimes(0);
        user.setStatus(0);
        user.setUserAvatarUrl("https://picx.zhimg.com/80/v2-9fa756b8c2eaaea33b8da2d48b95b60c_720w.webp?source=1940ef5c");
        if (userMapper.insert(user) > 0) {
            return Result.success("用户添加成功");
        } else {
            return Result.error("用户添加失败");
        }
    }

    public String updateUserStatus(String userId, Integer status) {
        int i = userMapper.updateUserStatus(status, userId);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }


    /**
     * 获取全部用户列表
     *
     * @return 用户列表
     */

    public String getUserList() {
        return Result.success(userMapper.selectList(null));
    }

    /**
     * 通过账号获取用户信息
     *
     * @param account
     * @return
     */
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    /**
     * 检查用户的状态
     *
     * @param userId
     * @return
     */
    @Override
    public Long checkUserStatus(String userId) {
        return userMapper.checkUserStatus(userId);
    }

    /**
     * 更新用户登录次数
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserLoginTimes(User user) {
        return userMapper.updateUserLoginTimes(user);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public int deleteUserById(String userId) {
        return userMapper.deleteUser(userId);
    }


    public String updateUser(User user) {
        int i = userMapper.updateUser(user);
        if (i > 0) {
            return Result.success(user);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除用户登录token
     *
     * @param user
     */
    public Boolean delLoginToken(User user) {
        return redisUtils.del(NomalEnum.LOGIN_TOKEN_PREFIX + user.getUserId());
    }

    /**
     * 验证用户登录token
     *
     * @param token
     */
    public String validateToken(String token, String userId) {
        HashMap<String, Object> map = new HashMap<>(16);
        if (token == null || token.isEmpty()) {
            Result<Object> r = new Result<>();
            r.setCode(ResultEnum.ERROR.getCode());
            r.setMsg(ResultEnum.ERROR.getMsg());
            map.put("data", "token is null");
            return JSONObject.toJSONString(r);
        }
        Result<Object> r = new Result<>();
        boolean expire = redisUtils.isExpire(NomalEnum.LOGIN_TOKEN_PREFIX + userId);
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMsg(ResultEnum.SUCCESS.getMsg());
        map.put("isExpire", expire);
        r.setData(map);
        return JSONObject.toJSONString(r);
    }

    /**
     * 添加用户登录token
     *
     * @param user
     * @return
     */
    public String setLoginToken(User user) {
        String loginToken = IdUtil.simpleUUID();
        Map<String, Object> map = new HashMap<>(16);
        map.put("token", loginToken);
        map.put("user", user);
        redisUtils.set(NomalEnum.LOGIN_TOKEN_PREFIX + user.getUserId(), map, LOGIN_EXPIRE);
        return loginToken;
    }

    /**
     * 添加用户注册时使用的token
     *
     * @param user
     * @return
     */
    public String setRegisterToken(User user) {
        Map<String, Object> map = new HashMap<>(16) {
            @Serial
            private static final long serialVersionUID = 1L;

            {
                put("user", user);
            }
        };
        String newToken = JWTUtil.createToken(map, user.getUserId().getBytes()).replace(".", "");
        redisUtils.set(NomalEnum.REGISTER_TOKEN_PREFIX + user.getUserId(), newToken, REGISTER_EXPIRE);
        return newToken;
    }

    /**
     * 激活新用户
     *
     * @param userId
     * @param token
     * @return
     */
    public String activatedUser(String userId, String token) {
        boolean res = redisUtils.isExpire(NomalEnum.REGISTER_TOKEN_PREFIX + userId);
        Result<Object> r = new Result<>();
        if (res) {
            int i = userMapper.updateUserStatus(0, userId);
            if (i > 0) {
                r.setCode(ResultEnum.REGISTER_ACTIVATED_SUCCESS.getCode());
                r.setMsg(ResultEnum.REGISTER_ACTIVATED_SUCCESS.getMsg());
                User user = userMapper.getUserByAccount(userId);
                UserBase userBase = new UserBase();
                userBase.setUserName(user.getUserName());
                userBase.setUserAvatarUrl(user.getUserAvatarUrl());
                userBase.setIntegral(user.getIntegral());
                userBase.setUserId(user.getUserId());
                String loginToken = setLoginToken(user);
                userBase.setToken(loginToken);
                r.setData(userBase);

            } else {
                r.setCode(ResultEnum.ERROR.getCode());
                r.setMsg(ResultEnum.ERROR.getMsg());
            }
        } else {
            r.setCode(ResultEnum.REGISTER_LINK_EXPIRE.getCode());
            r.setMsg(ResultEnum.REGISTER_LINK_EXPIRE.getMsg());
            User user = userMapper.getUserByAccount(userId);
            String newToken = setRegisterToken(user);
            iEmailService.senderActivatedEmail(user, new String[]{user.getEmail()}, newToken);
        }
        return JSONObject.toJSONString(r);
    }

    /**
     * 新用户注册业务
     *
     * @return String
     */
    public String registerUser(User user) {
        Result<Object> r = new Result<>();
        if ("".equals(user.getUserName()) || "".equals(user.getPassword())) {
            r.setCode(ResultEnum.PARAM_ERROR.getCode());
            r.setMsg(ResultEnum.PARAM_ERROR.getMsg());
            return JSONObject.toJSONString(r);
        }
        user.setUserId(String.valueOf(UniqueIdGeneratorUtils.IntegerIdGenerate()));
        user.setUserAvatarUrl("");
        user.setStatus(-1);
        user.setRoleId("10001");
        user.setCreateTime(DateUtil.now());
        user.setLoginTimes(0);
        user.setIntegral(0);
        //检验用户是否存在
        if (userMapper.getUserByAccount(user.getUserId()) != null) {
            r.setCode(ResultEnum.ACCOUNT_ALREADY_EXISTS.getCode());
            r.setMsg(ResultEnum.ACCOUNT_ALREADY_EXISTS.getMsg());
            return JSONObject.toJSONString(r);
        } else if (userMapper.getUserByAccount(user.getEmail()) != null) {
            r.setCode(ResultEnum.ACCOUNT_EMAIL_EXISTS.getCode());
            r.setMsg(ResultEnum.ACCOUNT_EMAIL_EXISTS.getMsg());
            return JSONObject.toJSONString(r);
        }
        int i = userMapper.addUserOne(user);
        if (i > 0) {
            String newToken = setRegisterToken(user);
            iEmailService.senderActivatedEmail(user, new String[]{user.getEmail()}, newToken);
            HashMap<String, String> hashMap = new HashMap<>(16);
            hashMap.put("userId", user.getUserId());
            r.setCode(ResultEnum.ACCOUNT_REGISTER_SUCCESS.getCode());
            r.setMsg(ResultEnum.ACCOUNT_REGISTER_SUCCESS.getMsg());
            r.setData(hashMap);
        } else {
            Result.error(ResultEnum.ERROR.getMsg());
        }
        return JSONObject.toJSONString(r);
    }


    /**
     * 用户登出
     *
     * @param user
     * @return
     */
    public String logOutAccount(User user) {
        Boolean aBoolean = delLoginToken(user);
        return Result.success(null);
    }

    /**
     * 密码登录验证
     *
     * @param account
     * @param password
     * @return
     */
    public String verifyAccount(String account, String password) {
        Result<Object> r = new Result<>();
        if (!"".equals(account) && !"".equals(password)) {
            User user = userMapper.getUserByAccount(account);
            if (user != null) {
                if (user.getStatus() != 0) {
                    if (user.getStatus() == -1) {
                        HashMap<String, String> o = new HashMap<>(16);
                        r.setCode(ResultEnum.ACCOUNT_NOT_ACTIVATED.getCode());
                        r.setMsg(ResultEnum.ACCOUNT_NOT_ACTIVATED.getMsg());
                        o.put("userId", user.getUserId());
                        o.put("userName", user.getUserName());
                        r.setData(o);
                        return JSONObject.toJSONString(r);
                    }
                    r.setCode(ResultEnum.ACCOUNT_ABNORMAL.getCode());
                    r.setMsg(ResultEnum.ACCOUNT_ABNORMAL.getMsg());
                } else {
                    //账号状态正常的情况下验证密码
                    if (user.getPassword().equals(password)) {
                        userMapper.updateUserLoginTimes(user);
                        r.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
                        r.setMsg(ResultEnum.LOGIN_SUCCESS.getMsg());
                        UserBase userBase = new UserBase();
                        userBase.setUserId(user.getUserId());
                        userBase.setUserName(user.getUserName());
                        userBase.setUserAvatarUrl(user.getUserAvatarUrl());
                        userBase.setIntegral(user.getIntegral());
                        userBase.setUserExp(user.getUserExp());
                        String loginToken = setLoginToken(user);
                        userBase.setToken(loginToken);
                        r.setData(userBase);
                    } else {
                        r.setCode(ResultEnum.LOGIN_FAILED.getCode());
                        r.setMsg(ResultEnum.LOGIN_FAILED.getMsg());
                        r.setData(null);
                    }
                }
            } else {
                return Result.error(ResultEnum.ERROR.getMsg());
            }
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
        return JSONObject.toJSONString(r);
    }

    /**
     * 检查用户是否激活
     *
     * @param userId
     * @return
     */
    public String checkUserActivated(String userId) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        Long status = userMapper.checkUserStatus(userId);
        if (status == null) {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
        if (status != -1 && status != 1) {
            hashMap.put("activated", true);
        } else {
            hashMap.put("activated", false);
        }
        return Result.success(hashMap);
    }
}
