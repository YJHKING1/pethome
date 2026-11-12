package org.yjhking.pethome.basic.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.yjhking.pethome.basic.annotation.PreAuthorize;
import org.yjhking.pethome.basic.util.LoginContext;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.jwt.JwtKeys;
import org.yjhking.pethome.user.jwt.JwtUtils;
import org.yjhking.pethome.user.jwt.LoginData;
import org.yjhking.pethome.user.jwt.Payload;
import org.yjhking.pethome.user.mapper.LogininfoMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final JwtKeys jwtKeys;
    private final LogininfoMapper logininfoMapper;

    public LoginInterceptor(JwtKeys jwtKeys, LogininfoMapper logininfoMapper) {
        this.jwtKeys = jwtKeys;
        this.logininfoMapper = logininfoMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            return reject(response, "noLogin");
        }

        LoginData loginData;
        try {
            Payload<LoginData> payload = JwtUtils.getInfoFromToken(token, jwtKeys.getPublicKey(), LoginData.class);
            loginData = payload.getLoginData();
        } catch (ExpiredJwtException e) {
            return reject(response, "timeout");
        } catch (JwtException | IllegalArgumentException e) {
            return reject(response, "noLogin");
        }
        if (loginData == null || loginData.getLogininfo() == null) {
            return reject(response, "noLogin");
        }

        Logininfo logininfo = loginData.getLogininfo();
        if (handler instanceof HandlerMethod) {
            PreAuthorize permission = ((HandlerMethod) handler).getMethodAnnotation(PreAuthorize.class);
            if (permission != null && (!Integer.valueOf(0).equals(logininfo.getType())
                    || !logininfoMapper.selectPermissionsByLogininfoId(logininfo.getId()).contains(permission.sn()))) {
                return reject(response, "noPermission");
            }
        }

        LoginContext.setLoginUser(request, logininfo);
        return true;
    }

    private boolean reject(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
        return false;
    }
}
