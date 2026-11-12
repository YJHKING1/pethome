package org.yjhking.pethome.user.constants;

/**
 * 微信登录常量类
 *
 * @author YJH
 */
public class WeiXinConstants {
    public static final String GET_ACK_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static final String GET_USER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
}
