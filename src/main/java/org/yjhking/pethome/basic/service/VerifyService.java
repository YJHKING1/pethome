package org.yjhking.pethome.basic.service;

import org.yjhking.pethome.basic.dto.MobileCodeDto;

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
    
    /**
     * 发送短信验证码
     *
     * @param mobileCodeDto 短信验证码DTO
     */
    void smsCode(MobileCodeDto mobileCodeDto);
    
    /**
     * 微信绑定验证码
     *
     * @param mobileCodeDto 短信验证码DTO
     */
    void binderSmsCode(MobileCodeDto mobileCodeDto);
}
