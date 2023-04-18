package com.yqg.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KIKO
 */
@TableName("user")
@Data
@NoArgsConstructor
public class User implements Serializable{
    String userId;
    String userName;
    String userAvatarUrl;
    String userDes;
    String userExp;
    String userTags;
    String password;
    Integer status;
    String email;
    String roleId;
    String createTime;
    Integer loginTimes;
    Integer integral;
}
