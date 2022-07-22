package org.yjhking.pethome.basic.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yjhking.pethome.basic.annotation.PreAuthorize;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.jwt.JwtUtils;
import org.yjhking.pethome.user.jwt.LoginData;
import org.yjhking.pethome.user.jwt.Payload;
import org.yjhking.pethome.user.jwt.RsaUtils;
import org.yjhking.pethome.user.mapper.LogininfoMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;
import java.util.List;

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
    @Autowired
    private LogininfoMapper logininfoMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取token
        String token = request.getHeader("token");
        //2.如果有token【jwtToken】执行里面的代码，没有就去登录
        if (token != null) {
            //3.获取公钥
            PublicKey publicKey = RsaUtils.getPublicKey(RsaUtils.class.getClassLoader().getResource("auth_rsa.pub").getFile());
            try {
                //4.将jwtToken解析成想要的数据：LoginData
                Payload<LoginData> payload = JwtUtils.getInfoFromToken(token, publicKey, LoginData.class);
                //刷新过期时间不做
                if (payload != null) {//登录成功，而且没有过期
                    //5.如获取登录信息对象
                    Logininfo logininfo = payload.getLoginData().getLogininfo();
                    //6.如果是用户 - 放行
                    if (logininfo.getType() == 1) {
                        return true;
                    }
                    // 7.如果是后端管理员 - 验证权限【后端管理员 - 角色不一样 - 权限不一样】
                    // 8.获取当前接口上注解里的sn - handler - 获取注解@PreAuthorize - 获取注解里面的sn
                    HandlerMethod handlerMethod = (HandlerMethod) handler; //就是接收请求的接口 - 方法
                    // System.out.println(handlerMethod.getMethod().getName());//获取接口名
                    PreAuthorize annotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
                    // 如果当前接口方法上没有这个注解 - 不需要校验权限 - 直接放行
                    if (annotation == null) {
                        return true;
                    } else {
                        String sn = annotation.sn();
                        // 9.根据logininfo_id获取当前登录人的所有权限 - sn
                        List<String> sns = logininfoMapper.selectPermissionsByLogininfoId(logininfo.getId());
                        if (sns.contains(sn)) {//有权限
                            return true;
                            // else会经过后置拦截器，可以在axios后置拦截器中判断弹出错误框
                            // 无权访问时的返回
                        } else {
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().println("{\"success\":false,\"message\":\"noPermission\"}");
                            return false;
                        }
                    }
                }
                // 过期时的返回
            } catch (ExpiredJwtException e) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println("{\"success\":false,\"message\":\"timeout\"}");
                return false;
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
