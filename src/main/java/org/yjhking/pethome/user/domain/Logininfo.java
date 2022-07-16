package org.yjhking.pethome.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Logininfo extends BaseDomain {
    private String username;
    
    private String phone;
    
    private String email;
    
    private String salt;
    
    private String password;
    
    private Integer type;
    
    private Boolean disable = true;
}