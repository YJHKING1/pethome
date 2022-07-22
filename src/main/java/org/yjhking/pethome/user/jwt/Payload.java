package org.yjhking.pethome.user.jwt;

import lombok.Data;

import java.util.Date;

@Data
public class Payload<T> {
    /**
     * jwt的id(token)
     */
    private String id;
    /**
     * 用户信息：用户数据，不确定，可以是任意类型
     */
    private T loginData;
    /**
     * 过期时间
     */
    private Date expiration;
}