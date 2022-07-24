package org.yjhking.pethome.basic.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.yjhking.pethome.user.jwt.JwtUtils;
import org.yjhking.pethome.user.jwt.LoginData;
import org.yjhking.pethome.user.jwt.Payload;
import org.yjhking.pethome.user.jwt.RsaUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

/**
 * 获取登录信息的上下文：把登录的相关信息放到这里面，方便调用
 *
 * @author YJH
 */
@Slf4j
public class LoginContext {
    /**
     * @param request 请求
     * @return 如果是后台需要返回employee，如果是前台返回user
     */
    public static Object getLoginUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if (!StringUtils.hasLength(token)) {
                return null;
            }
            //获取公钥
            PublicKey pubKey = RsaUtils.getPublicKey(JwtUtils.class.getClassLoader().getResource("auth_rsa.pub")
                    .getFile());
            //获取载荷 = 数据
            Payload<LoginData> payload = JwtUtils.getInfoFromToken(token, pubKey, LoginData.class);
            LoginData loginData = payload.getLoginData();
            return loginData.getLogininfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
