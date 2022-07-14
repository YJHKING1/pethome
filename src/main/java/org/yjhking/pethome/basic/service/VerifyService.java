package org.yjhking.pethome.basic.service;

/**
 * 验证码业务层接口
 *
 * @author YJH
 */
public interface VerifyService {
    /**
     * 生成图片验证码
     *
     * @param key UUID
     * @return base64格式的图片验证码
     */
    String getVerifyCode(String key);
}
