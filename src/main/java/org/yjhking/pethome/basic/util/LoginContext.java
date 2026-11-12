package org.yjhking.pethome.basic.util;

import org.yjhking.pethome.user.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;

public class LoginContext {
    private static final String LOGIN_USER = LoginContext.class.getName() + ".loginUser";

    public static Object getLoginUser(HttpServletRequest request) {
        return request.getAttribute(LOGIN_USER);
    }

    public static void setLoginUser(HttpServletRequest request, Logininfo logininfo) {
        request.setAttribute(LOGIN_USER, logininfo);
    }
}
