package com.yqg.controller;

import com.yqg.service.impl.UserServiceImpl;
import com.yqg.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author KIKO
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl userService;


    @RequestMapping("/user/list")
    public String getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/user/update")
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 更新用户的状态
     */
    @PostMapping("/user/updateStatus")
    public String updateUserStatus(@RequestParam("userId") String userId, @RequestParam("status") Integer status) {
        return userService.updateUserStatus(userId, status);
    }

    /**
     * 新增用户
     */
    @PostMapping("/user/add")
    public String addUser(@RequestBody User user) {
        return userService.adminAddUser(user);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return userService.adminDeleteUser(userId);
    }
}
