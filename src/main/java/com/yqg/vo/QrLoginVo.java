package com.yqg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KIKO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrLoginVo {
    String token;
    String id;
    String userName;
    String loginTime;
    String createTime;
    /**
     * 0有效，1失效
     */
    String QrStatus;

}
