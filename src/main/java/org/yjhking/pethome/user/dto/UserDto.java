package org.yjhking.pethome.user.dto;

import lombok.Data;

/**
 * 用户注册DTO
 *
 * @author YJH
 */
@Data
public class UserDto {
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String passwordRepeat;
    /**
     * 图片验证码
     */
    private String imageCode;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 手机验证码
     */
    private String phoneCode;
}
