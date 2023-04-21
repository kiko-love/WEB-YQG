package com.yqg.inteceptor;

import com.yqg.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KIKO
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String userId = request.getHeader("AuthorId");
        log.info("access_token:" + token);
        log.info("userId:" + userId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

         //检查是否存在 Token
//        if (token == null || token.isEmpty()) {
//            Result<Object> r = new Result<>();
//            r.setCode(ResultEnum.AUTHOR_EXPIRE_LACK.getCode());
//            r.setMsg(ResultEnum.AUTHOR_EXPIRE_LACK.getMsg());
//            response.getWriter().write(JSONObject.toJSONString(r));
//            return false;
//        }
//        // 检查是否存在 userId
//        if (userId == null || userId.isEmpty()) {
//            Result<Object> r = new Result<>();
//            r.setCode(ResultEnum.AUTHOR_PARAMS_LACK.getCode());
//            r.setMsg(ResultEnum.AUTHOR_PARAMS_LACK.getMsg());
//            response.getWriter().write(JSONObject.toJSONString(r));
//            return false;
//        }
//
//        // 解析 Token
//        Object o =  redisUtils.get(NomalEnum.LOGIN_TOKEN_PREFIX + userId);
//        if (o == null) {
//            Result<Object> r = new Result<>();
//            r.setCode(ResultEnum.AUTHOR_FAILED.getCode());
//            r.setMsg(ResultEnum.AUTHOR_FAILED.getMsg());
//            response.getWriter().write(JSONObject.toJSONString(r));
//            return false;
//        }
        return true;
    }

    /**
     * 将对象转换为 Map
     *
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(object);
            map.put(name, value);
        }
        return map;
    }

}
