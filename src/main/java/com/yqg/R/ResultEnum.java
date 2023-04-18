package com.yqg.R;

/**
 * @author KIKO
 */
public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(-110, "未知错误"),
    QR_EXPIRE(20110, "二维码过期"),
    QR_SCAN(20000, "用户已扫描二维码"),
    QR_UNCONFIRMED(20001, "用户未确认扫码登录"),
    QR_TOKEN_ERROR(20002, "二维码参数错误"),
    QR_PASS(20100, "用户扫码成功登录"),
    ERROR(110, "调用失败"),
    SUCCESS(100, "调用成功"),
    USER_NOT_EXIST(104, "用户不存在"),
    USER_IS_EXISTS(105, "用户已存在"),
    PARAM_ERROR(10010, "参数异常"),
    LOGIN_FAILED(210, "登录失败"),
    LOGIN_SUCCESS(220, "登录成功"),
    ACCOUNT_REGISTER_SUCCESS(300, "账号注册成功"),
    ACCOUNT_ALREADY_EXISTS(302,"用户已存在"),
    ACCOUNT_EMAIL_EXISTS(303,"邮箱已存在"),
    ACCOUNT_ABNORMAL(310, "帐号状态异常"),
    ACCOUNT_NOT_ACTIVATED(320, "账号未激活"),
    ACCOUNT_ACTIVATED(330, "账号已激活"),
    ACCOUNT_ACTIVATED_FAILED(340, "账号激活失败"),
    REGISTER_LINK_EXPIRE(60010, "激活链接已失效"),
    REGISTER_ACTIVATED_SUCCESS(60020, "账户激活成功"),
    REGISTER_ACTIVATED_FAILED(60030, "账户激活失败"),
    DATA_IS_NULL(106, "数据为空"),
    AUTHOR_FAILED(401, "无效授权信息"),
    AUTHOR_EXPIRE(402, "授权信息过期"),
    AUTHOR_EXPIRE_LACK(403, "授权信息缺失"),
    AUTHOR_PARAMS_ERROR(40000, "授权信息错误"),
    AUTHOR_PARAMS_LACK(40001, "授权信息缺失"),
    ;
    private final Integer code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
