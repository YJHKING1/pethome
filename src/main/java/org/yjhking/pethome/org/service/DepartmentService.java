package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.query.DepartmentQuery;

import java.util.List;

/**
 * 部门业务层接口
 *
 * @author YJH
 */
public interface DepartmentService {
    /**
     * 通过主键删除
     *
     * @param id 主键
     * @return 删除的id
     */
    int deleteByPrimaryKey(Long id);
    
    /**
     * 增加数据
     *
     * @param department 部门
     * @return 增加的id
     */
    int insertSelective(Department department);
    
    /**
     * 通过主键查询
     *
     * @param id 主键
     * @return 查询结果
     */
    Department selectByPrimaryKey(Long id);
    
    /**
     * 更新
     *
     * @param department 更新的数据
     * @return 更新的id
     */
    int updateByPrimaryKeySelective(Department department);
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    List<Department> selectAll();
    
    /**
     * 分页查询
     *
     * @param departmentQuery 当前页和每页条数
     * @return 部门集合
     */
    PageList<Department> queryData(DepartmentQuery departmentQuery);
}