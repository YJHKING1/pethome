package org.yjhking.pethome.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    
    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    @ToString.Exclude
    private String salt;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JSONField(serialize = false)
    @ToString.Exclude
    private String password;
    
    private Integer state;
    
    private Integer age;
    
    private Date createtime = new Date();
    
    private String headimg;
    
    private Long logininfoId;
}