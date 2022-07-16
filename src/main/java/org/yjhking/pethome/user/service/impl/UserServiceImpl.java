package org.yjhking.pethome.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.constants.VerifyCodeConstants;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.Md5Utils;
import org.yjhking.pethome.basic.util.StrUtils;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.dto.UserDto;
import org.yjhking.pethome.user.mapper.LogininfoMapper;
import org.yjhking.pethome.user.mapper.UserMapper;
import org.yjhking.pethome.user.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author YJH
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LogininfoMapper logininfoMapper;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        Long logininfoId = userMapper.selectByPrimaryKey(id).getLogininfoId();
        logininfoMapper.deleteByPrimaryKey(logininfoId);
        return super.deleteByPrimaryKey(id);
    }
    
    @Override
    public Integer insertSelective(User user) {
        // 获取32位随机盐值
        String salt = StrUtils.getComplexRandomString(32);
        // 获取MD5加密之后的密码
        String password = Md5Utils.encrypByMd5(user.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(password);
        Logininfo logininfo = user2Logininfo(user);
        logininfoMapper.insertSelective(logininfo);
        return super.insertSelective(user);
    }
    
    @Override
    public Integer updateByPrimaryKeySelective(User user) {
        // 获取32位随机盐值
        String salt = StrUtils.getComplexRandomString(32);
        // 获取MD5加密之后的密码
        String password = Md5Utils.encrypByMd5(user.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(password);
        Long logininfoId = userMapper.selectByPrimaryKey(user.getId()).getLogininfoId();
        Logininfo logininfo = user2Logininfo(user);
        logininfo.setId(logininfoId);
        logininfoMapper.updateByPrimaryKeySelective(logininfo);
        return super.updateByPrimaryKeySelective(user);
    }
    
    @Transactional
    @Override
    public void emailRegister(UserDto userDto) {
        // 注册信息的空值判断
        if (userDto.getEmail() == null || userDto.getEmail().trim().length() == 0) {
            throw new BusinessRuntimeException("邮箱不能为空");
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().length() == 0) {
            throw new BusinessRuntimeException("密码不能为空");
        }
        if (userDto.getPasswordRepeat() == null || userDto.getPasswordRepeat().trim().length() == 0) {
            throw new BusinessRuntimeException("确认密码不能为空");
        }
        // 判断两次密码是否一致
        if (!userDto.getPassword().equals(userDto.getPasswordRepeat())) {
            throw new BusinessRuntimeException("两次密码不一致");
        }
        // 类型转换
        User user = userDto2User(userDto);
        Logininfo logininfo = user2Logininfo(user);
        // 保存数据至数据库
        logininfoMapper.insertSelective(logininfo);
        user.setLogininfoId(logininfo.getId());
        user.setState(0);
        userMapper.insertSelective(user);
        // 发送激活邮件
        //创建复杂邮件对象
        try {
            // 发送激活邮件
            //创建复杂邮件对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //发送复杂邮件的工具类
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("910480155@qq.com");
            helper.setSubject("用户注册激活邮件");
            helper.setText("<h1>你的账户已经注册!!!</h1><h2>点击链接激活</h2><a src='http://localhost:8080/shop/active/"
                    + user.getId() + "'>http://localhost:8080/user/active/" + user.getId() + "</a>", true);
            //收件人
            String email = user.getEmail();
            helper.setTo(email);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessRuntimeException("邮件发送失败");
        }
    }
    
    @Transactional
    @Override
    public void phoneRegister(UserDto userDto) {
        // 空值校验
        if (userDto.getPhone() == null || userDto.getPhone().trim().length() == 0) {
            throw new BusinessRuntimeException("手机号不能为空");
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().length() == 0) {
            throw new BusinessRuntimeException("密码不能为空");
        }
        if (userDto.getPasswordRepeat() == null || userDto.getPasswordRepeat().trim().length() == 0) {
            throw new BusinessRuntimeException("确认密码不能为空");
        }
        if (userDto.getImageCode() == null || userDto.getImageCode().trim().length() == 0) {
            throw new BusinessRuntimeException("图片验证码不能为空");
        }
        if (userDto.getPhoneCode() == null || userDto.getPhoneCode().trim().length() == 0) {
            throw new BusinessRuntimeException("手机验证码不能为空");
        }
        // 密码一致校验
        if (!userDto.getPassword().equals(userDto.getPasswordRepeat())) {
            throw new BusinessRuntimeException("两次密码不一致");
        }
        // 手机验证码过期校验
        String phoneCode = (String) redisTemplate.opsForValue().get(VerifyCodeConstants.REGISTER_CODE_PREFIX + userDto.getPhone());
        if (phoneCode == null || phoneCode.trim().length() == 0) {
            throw new BusinessRuntimeException("手机验证码已过期");
        }
        phoneCode = phoneCode.split(":")[0];
        // 手机验证码正确校验
        if (!phoneCode.equals(userDto.getPhoneCode())) {
            throw new BusinessRuntimeException("手机验证码不正确");
        }
        // 类型转换
        User user = userDto2User(userDto);
        Logininfo logininfo = user2Logininfo(user);
        // 保存数据
        logininfoMapper.insertSelective(logininfo);
        user.setLogininfoId(logininfo.getId());
        userMapper.insertSelective(user);
    }
    
    @Transactional
    @Override
    public void active(Long id) {
        // 根据id查询用户信息
        User user = userMapper.selectByPrimaryKey(id);
        // 设置用户为启用
        user.setState(1);
        // 更新用户信息至数据库
        userMapper.updateByPrimaryKeySelective(user);
    }
    
    /**
     * userDto转User
     *
     * @param userDto userDto
     * @return User
     */
    private User userDto2User(UserDto userDto) {
        User user = new User();
        if (userDto.getEmail() != null && user.getEmail().trim().length() > 0) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != null && user.getPhone().trim().length() > 0) {
            user.setPhone(userDto.getPhone());
        }
        user.setState(1);
        String salt = StrUtils.getComplexRandomString(32);
        String password = Md5Utils.encrypByMd5(userDto.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(password);
        return user;
    }
    
    /**
     * user转Logininfo
     *
     * @param user user
     * @return Logininfo
     */
    private Logininfo user2Logininfo(User user) {
        Logininfo logininfo = new Logininfo();
        if (user.getEmail() != null && user.getEmail().trim().length() > 0) {
            logininfo.setEmail(user.getEmail());
        }
        if (user.getPhone() != null && user.getPhone().trim().length() > 0) {
            logininfo.setPhone(user.getPhone());
        }
        logininfo.setUsername(user.getUsername());
        logininfo.setSalt(user.getSalt());
        logininfo.setPassword(user.getPassword());
        // 设置类型为1，表示用户
        logininfo.setType(1);
        return logininfo;
    }
}
