package org.yjhking.pethome.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.constants.VerifyCodeConstants;
import org.yjhking.pethome.basic.dto.MobileCodeDto;
import org.yjhking.pethome.basic.service.VerifyService;
import org.yjhking.pethome.basic.util.StrUtils;
import org.yjhking.pethome.basic.util.VerifyCodeUtils;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.mapper.LogininfoMapper;
import org.yjhking.pethome.user.mapper.UserMapper;

import java.util.concurrent.TimeUnit;

/**
 * @author YJH
 */
@Service
public class VerifyServiceImpl implements VerifyService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogininfoMapper logininfoMapper;
    
    @Transactional
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
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        // 图片验证码宽度
        int codeImgWidth = 115;
        // 图片验证码高度
        int codeImgHeight = 40;
        // 把验证码的值转为base64格式的图片，并返回
        return VerifyCodeUtils.verifyCode(codeImgWidth, codeImgHeight, code);
    }
    
    @Transactional
    @Override
    public void smsCode(MobileCodeDto mobileCodeDto) {
        String phone = mobileCodeDto.getPhone();
        String imageCode = mobileCodeDto.getImageCode();
        String imageCodeKey = mobileCodeDto.getImageCodeKey();
        // 空值校验
        if (phone == null || phone.trim().length() == 0) {
            throw new BusinessRuntimeException("手机号不能为空");
        }
        if (imageCode == null || imageCode.trim().length() == 0) {
            throw new BusinessRuntimeException("验证码不能为空");
        }
        if (imageCodeKey == null || imageCodeKey.trim().length() == 0) {
            throw new BusinessRuntimeException("验证码校验Key不能为空");
        }
        // 获得redis中的图片验证码值
        String imageCodeKeyTrue = (String) redisTemplate.opsForValue().get(imageCodeKey);
        // 校验图片验证码是否过期
        if (imageCodeKeyTrue == null || imageCodeKeyTrue.trim().length() == 0) {
            throw new BusinessRuntimeException("图片验证码已过期");
        }
        // 校验图片验证码是否正确
        if (!imageCodeKeyTrue.equalsIgnoreCase(imageCode)) {
            throw new BusinessRuntimeException("图片验证码错误");
        }
        // 从数据库查询用户是否存在
        User user = userMapper.selectByPhone(phone);
        if (user != null) {
            throw new BusinessRuntimeException("该手机号已注册，请直接登录");
        }
        // 发送短信验证码
        getSmsCode(phone, VerifyCodeConstants.REGISTER_CODE_PREFIX);
    }
    
    @Override
    public void binderSmsCode(MobileCodeDto mobileCodeDto) {
        String phone = mobileCodeDto.getPhone();
        if (phone == null || phone.trim().length() == 0) {
            throw new BusinessRuntimeException("手机号不能为空");
        }
        // 发送短信验证码
        getSmsCode(phone, VerifyCodeConstants.BINDER_CODE_PREFIX);
    }
    
    /**
     * 发送短信验证码
     *
     * @param phone 要发送的手机号
     */
    private void getSmsCode(String phone, String prefix) {
        // 判断原来的验证码是否过期
        String codeString = (String) redisTemplate.opsForValue().get(prefix + phone);
        String code = null;
        if (codeString != null && codeString.trim().length() > 0) {
            String time = codeString.split(":")[1];
            long intervalTime = System.currentTimeMillis() - Long.parseLong(time);
            if (intervalTime <= 60 * 1000) {
                throw new BusinessRuntimeException("请勿重发验证码");
            } else {
                code = codeString.split(":")[0];
            }
        } else {
            int codeLength = 6;
            code = StrUtils.getRandomString(codeLength);
        }
        // 将验证码放入redis中，设置有效期为3分钟
        redisTemplate.opsForValue().set(prefix + phone,
                code + ":" + System.currentTimeMillis(), 3, TimeUnit.MINUTES);
        // 发短信
//        String smsCode = SmsUtils.sendSms(phone, "宠物乐园：您的验证码为：" + code + "请在3分钟内使用");
        // 测试
        System.out.println(phone + "的验证码为" + code);
    }
}
