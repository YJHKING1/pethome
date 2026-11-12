package org.yjhking.pethome.org.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * 员工
 *
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseDomain {
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
    /**
     * 验证密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JSONField(serialize = false)
    @ToString.Exclude
    private String comfirmPassword;
    
    private Integer age;
    
    private Integer state;
    
    private Long departmentId;
    /**
     * 部门
     */
    private Department department;
    
    private Long logininfoId;
    
    private Long shopId;
    /**
     * 店铺
     */
    private Shop shop;
    /**
     * 方便从中间表中为当前员工添加角色
     */
    private Long roleId;
}