package org.yjhking.pethome.org.mapper;

import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.query.DepartmentQuery;

import java.util.List;

/**
 * 部门
 *
 * @author YJH
 */
public interface DepartmentMapper {
    /**
     * 通过主键删除
     *
     * @param id 主键
     * @return 删除的id
     */
    Long deleteByPrimaryKey(Long id);
    
    /**
     * 增加数据
     *
     * @param department 部门
     * @return 增加的id
     */
    Long insertSelective(Department department);
    
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
    Long updateByPrimaryKeySelective(Department department);
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    List<Department> selectAll();
    
    /**
     * 查询总条数
     *
     * @param departmentQuery 当前页和每页条数
     * @return 总条数
     */
    Long queryCount(DepartmentQuery departmentQuery);
    
    /**
     * 分页查询
     *
     * @param departmentQuery 当前页和每页条数
     * @return 查询结果
     */
    List<Department> queryData(DepartmentQuery departmentQuery);
    
    void patchDelete(List<Long> ids);
}