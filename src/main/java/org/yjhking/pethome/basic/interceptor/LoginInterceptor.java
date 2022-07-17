package org.yjhking.pethome.basic.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 *
 * @author YJH
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader("token");
        // 如果有token，通过token获取redis的登录信息
        if (token != null) {
            // 获得logininfo对象
            Object obj = redisTemplate.opsForValue().get(token);
            // 判断logininfo是否为空
            if (obj != null) {
                // 如果不为空则放行，并刷新过期时间
                redisTemplate.opsForValue().set(token, obj, 30, TimeUnit.MINUTES);
                return true;
            }
        }
        // 告诉前端我要发送的参数类型为json，并转码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // 发送给前端的数据
        response.getWriter().println("{\"success\":false,\"msg\":\"noLogin\"}");
        return false;
    }
}
