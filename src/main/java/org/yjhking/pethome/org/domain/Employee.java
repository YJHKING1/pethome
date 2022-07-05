package org.yjhking.pethome.org.domain;

import lombok.Data;

/**
 * 员工
 *
 * @author YJH
 */
@Data
public class Employee {
    private Long id;
    
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
}