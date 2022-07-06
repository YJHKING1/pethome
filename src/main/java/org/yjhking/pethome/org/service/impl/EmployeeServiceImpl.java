package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.service.EmployeeService;

/**
 * 部门业务层
 *
 * @author YJH
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Transactional
    @Override
    public Integer insertSelective(Employee employee) {
        // 新增到数据库
        employeeMapper.insertSelective(employee);
        // 将登录id设为新增的id
        employee.setLogininfoId(employee.getId());
        // 更新
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    @Transactional
    @Override
    public Integer updateByPrimaryKeySelective(Employee employee) {
        // 将登录id设为id
        employee.setLogininfoId(employee.getId());
        // 更新到数据库
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
}