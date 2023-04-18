package com.yqg.utils;

import com.yqg.R.Result;
import com.yqg.R.ResultEnum;

/**
 * @author KIKO
 */
public class ResultUtils {
    /**
     * 成功且返回体有数据
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result r = new Result();
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMsg(ResultEnum.SUCCESS.getMsg());
        r.setData(object);
        return r;
    }

    /**
     * 成功但返回体没数据
     * @return
     */
    public static  Result success(){
        return success(null);
    }

    /**
     * 失败返回信息
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
