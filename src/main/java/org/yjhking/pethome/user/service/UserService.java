package org.yjhking.pethome.user.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.user.domain.User;
import org.yjhking.pethome.user.dto.UserDto;

/**
 * @author YJH
 */
public interface UserService extends BaseService<User> {
    /**
     * 用户注册业务层接口
     *
     * @param userDto 用户注册信息
     */
    void register(UserDto userDto);
}
