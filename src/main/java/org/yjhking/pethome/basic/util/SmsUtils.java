package org.yjhking.pethome.basic.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 短信发送工具类
 *
 * @author YJH
 */
public class SmsUtils {
    
    /**
     * 用户名
     */
    public static final String UID = "superstar";
    /**
     * 秘钥
     */
    public static final String KEY = "d41d8cd98f00b204e980";
    
    /**
     * 发送短信
     *
     * @param phones  接受短信的手机号码，多个号码用英文逗号隔开
     * @param content 发送内容
     * @return 发送结果状态码
     * 大于0  提交成功，短信发送数量；
     * -1  没有该用户账户；
     * -2  接口密钥不正确，不是账户登陆密码；
     * -21  MD5接口密钥加密不正确；
     * -3  短信数量不足；
     * -11  该用户被禁用；
     * -14  短信内容出现非法字符；
     * -4  手机号格式不正确；
     * -41  手机号码为空；
     * -42  短信内容为空；
     * -51  短信签名格式不正确，接口签名格式为：【签名内容】；
     * -52  短信签名太长，建议签名10个字符以内；
     * -6  IP限制
     */
    public static String sendSms(String phones, String content) {
        PostMethod post = null;
        try {
            HttpClient client = new HttpClient();
            post = new PostMethod("https://utf8api.smschinese.cn/");
            // 在头文件中设置转码
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            NameValuePair[] data = {new NameValuePair("Uid", "YJHKING"),
                    new NameValuePair("Key", "F1C981A4FD8BFE2067A0865F5A538B05"),
                    new NameValuePair("smsMob", phones),
                    new NameValuePair("smsText", content)};
            post.setRequestBody(data);
            client.executeMethod(post);
            // 返回消息状态
            return new String(post.getResponseBodyAsString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }
}