package com.yqg.controller;

import com.yqg.service.impl.UserServiceImpl;
import com.yqg.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author KIKO
 */
@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 新用户注册
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping ("/new")
    public String addNewOne(@RequestBody User user){
        return userService.registerUser(user);
    }

    /**
     * 激活新用户
     * @param userId
     * @param token
     * @return
     */
    @ResponseBody
    @RequestMapping("/activated/{userId}/{token}")
    public String activatedNew(@PathVariable("userId") String userId,
            @PathVariable("token") String token) {
        return userService.activatedUser(userId, token);
    }

    /**
     * 检查用户是否激活
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/isActivated/{userId}")
    public String isActivated(@PathVariable("userId") String userId) {
        return userService.checkUserActivated(userId);
    }

}
