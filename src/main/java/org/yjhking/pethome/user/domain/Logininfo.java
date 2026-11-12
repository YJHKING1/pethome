package org.yjhking.pethome.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    @ToString.Exclude
    private String salt;
    
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    @ToString.Exclude
    private String password;
    
    private Integer type;
    
    private Boolean disable = true;
}