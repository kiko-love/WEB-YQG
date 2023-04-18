package com.yqg.R;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author KIKO
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static String success(Object data) {
        Result<Object> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg("success");
        if (data != null) {
            result.setData(data);
        }
        return JSONObject.toJSONString(result);
    }

    public static String error(String msg) {
        Result<Object> result = new Result<>();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(msg == null ? ResultEnum.ERROR.getMsg() : msg);
        return JSONObject.toJSONString(result);
    }
}
