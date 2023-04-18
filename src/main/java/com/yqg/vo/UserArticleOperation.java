package com.yqg.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KIKO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserArticleOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userId;
    private String articleId;
    private Integer operationType;
    private String data;
    private String createTime;

    @TableField(exist = false)
    private Integer value;

    public Integer getUserId() {
        return Integer.parseInt(userId);
    }

    public Integer getArticleId() {
        return Integer.parseInt(articleId);
    }

}
