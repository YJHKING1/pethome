package org.yjhking.pethome.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.service.VerifyService;
import org.yjhking.pethome.basic.util.VerifyCodeUtils;

/**
 * @author YJH
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public String getVerifyCode(String key) {
        // 判断UUID不为空
        if (key == null || key.trim().length() == 0) {
            throw new BusinessRuntimeException("UUID不能为空");
        }
        // 验证码长度
        int codeLength = 4;
        // 生成随机验证码
        String code = VerifyCodeUtils.generateVerifyCode(codeLength);
        // 把验证码的值存储到Redis,以前台传入的UUID作为key
        redisTemplate.opsForValue().set(key, code);
        // 图片验证码宽度
        int codeImgWidth = 115;
        // 图片验证码高度
        int codeImgHeight = 40;
        // 把验证码的值转为base64格式的图片，并返回
        return VerifyCodeUtils.verifyCode(codeImgWidth, codeImgHeight, code);
    }
}
