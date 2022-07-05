package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.query.EmployeeQuery;

import java.util.List;

/**
 * 部门业务层接口
 *
 * @author YJH
 */
public interface EmployeeService {
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
     * @param employee 部门
     * @return 增加的id
     */
    Long insertSelective(Employee employee);
    
    /**
     * 通过主键查询
     *
     * @param id 主键
     * @return 查询结果
     */
    Employee selectByPrimaryKey(Long id);
    
    /**
     * 更新
     *
     * @param employee 更新的数据
     * @return 更新的id
     */
    Long updateByPrimaryKeySelective(Employee employee);
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    List<Employee> selectAll();
    
    /**
     * 分页查询
     *
     * @param employeeQuery 当前页和每页条数
     * @return 部门集合
     */
    PageList<Employee> queryData(EmployeeQuery employeeQuery);
    
    /**
     * 批量删除
     *
     * @param ids 删除的id集合
     */
    void patchDelete(List<Long> ids);
    
    /**
     * 部门树
     *
     * @return 部门树集合
     */
    /*List<Employee> deptTree();*/
}