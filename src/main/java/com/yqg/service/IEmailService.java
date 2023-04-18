package com.yqg.service;

import com.yqg.vo.User;
import org.springframework.scheduling.annotation.Async;

/**
 * @author KIKO
 */
public interface IEmailService {
    /**
     * 注册发送激活邮箱
     *
     * @param user
     * @param email
     * @param token
     */
    void senderActivatedEmail(User user,String[] email,String token);
}
