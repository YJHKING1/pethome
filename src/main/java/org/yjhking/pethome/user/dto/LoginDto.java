package org.yjhking.pethome.user.dto;

import lombok.Data;


/**
 * @author YJH
 */
@Data
public class LoginDto {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String checkPass;
    /**
     * 登录类型： 0：管理员 1：用户
     */
    private Integer loginType;
}
