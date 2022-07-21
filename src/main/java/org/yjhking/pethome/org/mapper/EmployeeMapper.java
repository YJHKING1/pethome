package org.yjhking.pethome.org.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.org.domain.Employee;

import java.util.List;

/**
 * 员工
 *
 * @author YJH
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    void deleteEmployeeRoleByEmployeeId(Long id);
    
    void insertEmployeeRole(Employee employee);
    
    List<String> selectPerssionSnByLogininfoId(Long id);
}