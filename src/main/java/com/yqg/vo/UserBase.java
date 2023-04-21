package com.yqg.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@Data
@NoArgsConstructor
public class UserBase {
    String userId;
    String userName;
    String userAvatarUrl;
    String userDes;
    Integer userExp;
    String userTags;
    Integer integral;
    /**
     * 登录令牌
     */
    String token;
}
