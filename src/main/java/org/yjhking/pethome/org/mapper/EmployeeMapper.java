package org.yjhking.pethome.org.mapper;

import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.query.EmployeeQuery;

import java.util.List;

/**
 * 员工
 *
 * @author YJH
 */
public interface EmployeeMapper {
    /**
     * 通过主键删除
     *
     * @param id 主键
     * @return 删除的id
     */
    int deleteByPrimaryKey(Long id);
    
    /**
     * 新增
     *
     * @param employee 新增的数据
     * @return 新增的id
     */
    int insertSelective(Employee employee);
    
    /**
     * 通过主键查询
     *
     * @param id 要查询的id
     * @return 查询结果
     */
    Employee selectByPrimaryKey(Long id);
    
    /**
     * 更新
     *
     * @param employee 更新的数据
     * @return 更新的id
     */
    int updateByPrimaryKeySelective(Employee employee);
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    List<Employee> selectAll();
    
    /**
     * 查询总条数
     *
     * @param employeeQuery 当前页和每页条数
     * @return 总条数
     */
    Long queryCount(EmployeeQuery employeeQuery);
    
    /**
     * 分页查询
     *
     * @param employeeQuery 当前页和每页条数
     * @return 查询结果
     */
    List<Employee> queryData(EmployeeQuery employeeQuery);
}