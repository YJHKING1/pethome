package org.yjhking.pethome.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.dto.UserDto;
import org.yjhking.pethome.user.mapper.LogininfoMapper;
import org.yjhking.pethome.user.mapper.UserMapper;
import org.yjhking.pethome.user.service.UserService;

/**
 * @author YJH
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogininfoMapper logininfoMapper;
    
    @Override
    public void register(UserDto userDto) {
        // 注册信息的空值判断
        if (userDto.getPassword() == null || userDto.getPassword().trim().length() == 0
                || userDto.getPasswordRepeat() == null || userDto.getPasswordRepeat().trim().length() == 0
                || userDto.getEmail() == null || userDto.getEmail().trim().length() == 0) {
            throw new BusinessRuntimeException("信息不能为空");
        }
        // 判断两次密码是否一致
        if (!userDto.getPassword().equals(userDto.getPasswordRepeat())) {
            throw new BusinessRuntimeException("两次密码不一致");
        }
        // 保存数据至数据库
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        
        Logininfo logininfo = new Logininfo();
        logininfo.setEmail(userDto.getEmail());
        logininfo.setPassword(userDto.getPassword());
        
        userMapper.insertSelective(user);
        logininfoMapper.insertSelective(logininfo);
        // todo 发送邮件
    }
}
