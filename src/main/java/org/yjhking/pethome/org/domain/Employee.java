package org.yjhking.pethome.org.domain;

import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * 员工
 *
 * @author YJH
 */
@Data
public class Employee extends BaseDomain {
    private String username;
    
    private String phone;
    
    private String email;
    
    private String salt;
    
    private String password;
    
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
}