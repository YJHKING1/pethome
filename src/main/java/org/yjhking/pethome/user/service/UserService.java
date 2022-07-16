package org.yjhking.pethome.user.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.dto.UserDto;

/**
 * @author YJH
 */
public interface UserService extends BaseService<User> {
    /**
     * 邮箱注册业务层接口
     *
     * @param userDto 用户注册信息
     */
    void emailRegister(UserDto userDto);
    
    /**
     * 手机注册业务层接口
     *
     * @param userDto 用户注册信息
     */
    void phoneRegister(UserDto userDto);
    
    /**
     * 邮箱激活业务层接口
     *
     * @param id 激活id
     */
    void active(Long id);
}
