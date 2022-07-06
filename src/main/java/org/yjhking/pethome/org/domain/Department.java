package org.yjhking.pethome.org.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 *
 * @author YJH
 */
@Data
public class Department extends BaseDomain {
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
    
    // 用于封装当前部门的子部门
    // 部门树最后一级没有数据就不显示
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Department> children = new ArrayList<>();
}