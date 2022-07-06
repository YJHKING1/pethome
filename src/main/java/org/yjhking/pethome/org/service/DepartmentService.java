package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.org.domain.Department;

import java.util.List;

/**
 * 部门业务层接口
 *
 * @author YJH
 */
public interface DepartmentService extends BaseService<Department> {
    /**
     * 部门树
     *
     * @return 部门树集合
     */
    List<Department> deptTree();
}