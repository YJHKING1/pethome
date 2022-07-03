package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.query.EmployeeQuery;
import org.yjhking.pethome.org.service.EmployeeService;

import java.util.List;

/**
 * 员工业务层
 *
 * @author YJH
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Transactional
    @Override
    public Long deleteByPrimaryKey(Long id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long insertSelective(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }
    
    @Override
    public Employee selectByPrimaryKey(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long updateByPrimaryKeySelective(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    @Override
    public List<Employee> selectAll() {
        return employeeMapper.selectAll();
    }
    
    @Override
    public PageList<Employee> queryData(EmployeeQuery employeeQuery) {
        // 查询总条数
        Long queryCount = employeeMapper.queryCount(employeeQuery);
        // 如果查询结果为0，则直接返回空集合
        if (queryCount < 1) {
            return new PageList<Employee>();
        }
        List<Employee> employees = employeeMapper.queryData(employeeQuery);
        return new PageList<Employee>(queryCount, employees);
    }
}
