package org.yjhking.pethome.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.Md5Utils;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.dto.LoginDto;
import org.yjhking.pethome.user.mapper.LogininfoMapper;
import org.yjhking.pethome.user.service.LogininfoService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录业务层
 *
 * @author YJH
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo> implements LogininfoService {
    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public AjaxResult account(LoginDto loginDto) {
        // 提取所有参数
        String account = loginDto.getAccount();
        String checkPass = loginDto.getCheckPass();
        Integer loginType = loginDto.getLoginType();
        // 空值校验
        if (account == null || account.trim().length() == 0
                || checkPass == null || checkPass.trim().length() == 0
                || loginType == null) {
            throw new BusinessRuntimeException("参数不能为空");
        }
        // 校验账号是否存在
        Logininfo logininfo = logininfoMapper.selectByAccount(loginDto);
        if (logininfo == null) {
            throw new BusinessRuntimeException("账号不存在");
        }
        // 获取数据库中的密码
        String dbPassword = logininfo.getPassword();
        // 获取前端密码并加盐加密
        String password = Md5Utils.encrypByMd5(checkPass + logininfo.getSalt());
        // 校验密码是否正确
        if (!dbPassword.equals(password)) {
            throw new BusinessRuntimeException("密码错误");
        }
        // 校验账号是否被禁用
        if (!logininfo.getDisable()) {
            throw new BusinessRuntimeException("账号已被禁用");
        }
        // 生成tokens
        String token = UUID.randomUUID().toString();
        // 将盐值和密码设为空
        logininfo.setSalt(null);
        logininfo.setPassword(null);
        // 保存到redis
        // redis保存对象需先序列化，再保存，否则会报错。解决办法：在要保存的实体类上实现Serializable接口即可。
        redisTemplate.opsForValue().set(token, logininfo, 30, TimeUnit.MINUTES);
        // 创建map用于返回
        Map<String, Object> map = new HashMap<>();
        // 将token可登录信息对象装入map
        map.put("token", token);
        map.put("logininfo", logininfo);
        // 返回map
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setResultObj(map);
        return ajaxResult;
    }
}
