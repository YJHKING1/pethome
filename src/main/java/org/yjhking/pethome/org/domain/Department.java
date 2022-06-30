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
    
    private String dirpath;
    
    private Integer state;
    
    private Long managerId;
    
    private Long parentId;
}