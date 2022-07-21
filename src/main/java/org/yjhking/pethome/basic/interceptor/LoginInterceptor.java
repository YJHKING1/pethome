package org.yjhking.pethome.basic.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yjhking.pethome.basic.annotation.PreAuthorize;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.user.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
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
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取token
        String token = request.getHeader("token");
        //2.如果token不为空：再去获取登录信息
        if (token != null) {
            //2.1.获取登录信息
            Object obj = redisTemplate.opsForValue().get(token);
            //2.2.登录信息不为空：登录了，并且没有过期
            if (obj != null) {
                //2.2.1.刷新登录过期时间
                redisTemplate.opsForValue().set(token, obj, 30, TimeUnit.MINUTES);
                
                //校验权限
                //a.登录成功权限1 - 如果是前台用户 - 直接放行【前台用户只需要登录成功即可】
                Logininfo logininfo = (Logininfo) obj;
                if (logininfo.getType().intValue() == 1) {
                    //1.如果是前端用户 user ，不需要校验任何权限，直接放行即可
                    return true;//放行
                }
                
                //b.登录成功校验2 - 如果是后台用户 - 继续校验权限
                //b1.哪些请求需要校验权限 （答：打了自定义注解PreAuthorize权限的方法）
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                //b2.获取当前请求/接口/controller中方法上的权限信息 - 是否有自定义注解@PreAuthorize
                PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
                //b3.如果preAuthorize为空：该方法中上没有加这个注解 - 说明公共资源不需要校验权限 - 放行
                if (preAuthorize == null) {
                    //b31. 没有就放行,公共资源不需要校验权限
                    return true;
                } else {//该方法中上加了这个注解 - 说明特殊资源需要校验权限
                    //b32.有就校验是否有权限访问 - 获取注解上的value值
                    String sn = preAuthorize.sn();
                    //b33.查询出当前登录人所拥有的权限：t_permission表中的sn
                    List<String> ownPermissions = employeeMapper.selectPerssionSnByLogininfoId(logininfo.getId());
                    //b34.如果集合中包含当前sn - 说明有权限访问 - 放行
                    if (ownPermissions.contains(sn)) {
                        return true;//终于有权限访问了
                    }
                    //b35.如果不包含：告诉他没有权限，请他去联系管理员
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.print("{\"success\":false,\"message\":\"noPermission\"}");
                    writer.close();
                    return false;//阻止放行
                }
            }
        }
        
        //3.如果token为空：没有登录，获取登录过期 - 由于异步请求，所以只能响应错误信息给前端，再前端跳转到登录页面
        //后端无法跳转到登录页面
        response.setCharacterEncoding("UTF-8");
        //告诉浏览器我要给你响应一个json数据，用Utf-8编码
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"noLogin\"}");
        return false;
    }
}
