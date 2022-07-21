package org.yjhking.pethome.org.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
    
    private String salt;
    
    private String password;
    /**
     * 验证密码
     */
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