package org.yjhking.pethome.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.Date;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDomain {
    private String username;
    
    private String phone;
    
    private String email;
    
    private String salt;
    
    private String password;
    
    private Integer state;
    
    private Integer age;
    
    private Date createtime = new Date();
    
    private String headimg;
    
    private Long logininfoId;
}