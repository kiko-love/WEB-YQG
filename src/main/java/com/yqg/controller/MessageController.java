package com.yqg.controller;

import com.yqg.service.impl.MessageServiceImpl;
import com.yqg.vo.BroadcastMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KIKO
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageServiceImpl messageService;

    /**
     * 广播消息
     *
     * @return
     */
    @RequestMapping("/broadcast")
    @ResponseBody
    public String broadcastMessage(@RequestBody BroadcastMsg message) {
        return messageService.broadcastMessage(message);
    }

    @RequestMapping("/getMsgUid/{userId}")
    @ResponseBody
    public String getMsgUid(@PathVariable("userId") String userId) {
        return messageService.getMsgUid(userId);
    }

}
