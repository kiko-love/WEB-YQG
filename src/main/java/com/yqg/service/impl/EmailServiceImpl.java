package com.yqg.service.impl;

import com.yqg.service.IEmailService;
import com.yqg.vo.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author KIKO
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送激活邮件(异步调用)
     * @param user
     * @param email
     * @param token
     */
    @Async("taskExecutor")
    @Override
    public void senderActivatedEmail(User user, String[] email, String token) {
        log.info(Thread.currentThread().getName());
        //一个复杂的邮件
        MimeMessage message = this.javaMailSender.createMimeMessage();
        try {
            //组装
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //正文
            //主题
            helper.setSubject("猿趣阁账户激活");
            //开启html模式
            helper.setText("<h1>欢迎注册猿趣阁</h1>" +
                            "<p>欢迎您:" + user.getUserName() + "</p>" +
                            "<p>您刚刚使用了该邮箱注册了新账号</p>" +
                            "<p>请点击下面的链接来验证激活您的账户</p>" +
                            "<a href=\"" + "http://localhost:3000/activate/" +
                            token+"/"+user.getUserId() + "\">验证邮箱</a>",
                    true);
            helper.setTo(email);
            helper.setFrom("3300303281@qq.com");
            //以outlook名义发送邮件，不会被当作垃圾邮件
            message.setHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.5512");
            message.setHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.5512");
            message.setHeader("ReturnReceipt", "1");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
