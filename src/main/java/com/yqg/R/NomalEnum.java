package com.yqg.R;

/**
 * @author KIKO
 */

public enum NomalEnum {
    /**
     * 登录token前缀
     */
    LOGIN_TOKEN_PREFIX("ACCESS_TOKEN"),
    /**
     * 注册token前缀
     */
    REGISTER_TOKEN_PREFIX("REGISTER_TOKEN"),
    /**
     * 二维码token前缀
     */
    QR_TOKEN_PREFIX("QR_TOKEN"),
    MSG_KEY("WEBSOCKET_MSG_KEY"),
    LOGIN_KEY("WEBSOCKET_LOGIN_KEY"),;
    private final String info;
    NomalEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
