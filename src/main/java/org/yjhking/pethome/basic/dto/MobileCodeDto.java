package org.yjhking.pethome.basic.dto;

import lombok.Data;

/**
 * 手机验证码DTO
 *
 * @author YJH
 */
@Data
public class MobileCodeDto {
    // 手机
    private String phone;
    // 验证码
    private String imageCode;
    // 验证码校验Key
    private String imageCodeKey;
}
