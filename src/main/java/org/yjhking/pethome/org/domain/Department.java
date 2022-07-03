package org.yjhking.pethome.org.domain;

import lombok.Data;

/**
 * 部门
 *
 * @author YJH
 */
@Data
public class Department {
    private Long id;
    
    private String sn;
    
    private String name;
    
    private String dirPath;
    
    private Integer state;
    
    private Long managerId;
    
    /**
     * 部门经理
     */
    private Employee manager;
    
    private Long parentId;
    
    /**
     * 上级部门
     */
    private Department parent;
}