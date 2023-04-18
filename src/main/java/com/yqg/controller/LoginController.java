package com.yqg.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yqg.R.NomalEnum;
import com.yqg.R.Result;
import com.yqg.R.ResultEnum;
import com.yqg.service.impl.UserServiceImpl;
import com.yqg.utils.DelayQrExpire;
import com.yqg.utils.LoginUtils;
import com.yqg.utils.RedisUtils;
import com.yqg.vo.User;
import com.yqg.websocket.WebSocketLoginServer;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author KIKO
 */
@CrossOrigin
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    private static final String FRONTED_PATH = "localhost:3000";
    @Autowired
    private DelayQrExpire delayQrExpire;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserServiceImpl userService;


    @ResponseBody
    @PostMapping("/validateToken")
    public String validateToken(@Param("token")String token,@Param("userId")String userId) {
        return userService.validateToken(token,userId);
    }

    @ResponseBody
    @RequestMapping("/out")
    public String accountLogout(@RequestBody User user) {
        return userService.logOutAccount(user);
    }

    /**
     * 密码登录接口
     *
     * @param account
     * @param password
     * @return
     */
    @ResponseBody
    @PostMapping("/account")
    public String accountLogin(@Param("account") String account,
                               @Param("password") String password) {
        return userService.verifyAccount(account, password);
    }

    /**
     * 手机客户端确认扫码登录（mock）
     *
     * @param uid
     * @param userId
     * @param token
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/confirmQr/{userId}/{uid}/{token}")
    public String bindQrLogin(@PathVariable("uid") String uid
            , @PathVariable("userId") String userId,
                              @PathVariable("token") String token) throws IOException, IllegalAccessException {
        Result<Object> r = new Result<>();
        String tokenKey = "token";
        String userKey = "user";
        Map<String, Object> map =
                LoginUtils.objectToMap(redisUtils.get(NomalEnum.LOGIN_TOKEN_PREFIX + userId));
        String qrToken = String.valueOf(redisUtils.get(NomalEnum.QR_TOKEN_PREFIX + userId));
        if (qrToken != null) {
            if (qrToken.equals(uid) && token.equals(map.get(tokenKey))) {
                r.setCode(ResultEnum.QR_PASS.getCode());
                r.setMsg(ResultEnum.QR_PASS.getMsg());
                r.setData(map.get(userKey));
                WebSocketLoginServer.sendInfo(JSONObject.toJSONString(r), uid);
            } else {
                r.setCode(ResultEnum.QR_TOKEN_ERROR.getCode());
                r.setCode(ResultEnum.QR_TOKEN_ERROR.getCode());
            }
        } else {
            r.setCode(ResultEnum.QR_EXPIRE.getCode());
            r.setCode(ResultEnum.QR_EXPIRE.getCode());
        }
        return JSONObject.toJSONString(r);
    }

    /**
     * 确认用户进行扫码操作，并接收二维码中的uid与用户id进行登录校验（此时为已扫码待确认状态）
     *
     * @param uid
     * @param userId
     * @return
     * @throws IOException
     */
    @RequestMapping("/scan/{uid}/userId/{userId}")
    @ResponseBody
    public String scanLoginQr(@PathVariable("uid") String uid
            , @PathVariable("userId") String userId) throws IOException {
        Result<Object> r = new Result<>();
        r.setCode(ResultEnum.QR_SCAN.getCode());
        r.setMsg(ResultEnum.QR_SCAN.getMsg());
        r.setData(null);
        String res = JSONObject.toJSONString(r);
        WebSocketLoginServer.sendInfo(res, uid);
        return res;
    }

    /**
     * 获取登录二维码、放入Token
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getLoginQr", method = RequestMethod.GET)
    public void createCodeImg(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Access-Control-Expose-Headers", "token");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            //设置二维码有效时间
            long expireTime = 60L;
            //生成唯一标识uid
            String uid = IdUtil.simpleUUID();
            String userId = request.getParameter("userId");
            redisUtils.set(NomalEnum.QR_TOKEN_PREFIX + userId, uid, expireTime);
            String qrContent = FRONTED_PATH + "/login/scan?uid=" + uid;
            response.setHeader("token", uid);
            QrConfig config = new QrConfig(300, 300);
            // 高纠错级别，用于增强二维码的识别精度
            config.setErrorCorrection(ErrorCorrectionLevel.H);
            ClassPathResource classPathResource = new ClassPathResource("static/favicon.png");
            config = QrConfig.create()
                    .setImg(classPathResource.getPath());
            QrCodeUtil.generate(qrContent, config, "jpeg", response.getOutputStream());
            delayQrExpire.delayQrExpire(NomalEnum.QR_TOKEN_PREFIX + userId, uid, expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
