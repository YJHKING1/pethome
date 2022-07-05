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
 * 部门业务层
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
        // 新增到数据库
        employeeMapper.insertSelective(employee);
        // 将登录id设为新增的id
        employee.setLogininfoId(employee.getId());
        // 更新
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    @Override
    public Employee selectByPrimaryKey(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long updateByPrimaryKeySelective(Employee employee) {
        // 将登录id设为id
        employee.setLogininfoId(employee.getId());
        // 更新到数据库
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
        // 如果查询总条数为0，则直接返回空集合
        if (queryCount < 1) {
            return new PageList<Employee>();
        }
        // 查询结果
        List<Employee> employees = employeeMapper.queryData(employeeQuery);
        return new PageList<Employee>(queryCount, employees);
    }
    
    @Override
    public void patchDelete(List<Long> ids) {
        employeeMapper.patchDelete(ids);
    }
    
    /*@Override
    public List<Employee> deptTree() {
        // 查询所有部门，装进集合
        List<Employee> employees = employeeMapper.selectAll();
        // 新建map，键为部门id，值为部门
        Map<Long, Employee> map = new HashMap<>();
        // 遍历部门集合，装进map
        for (Employee employee : employees) {
            map.put(employee.getId(), employee);
        }
        // 新建部门树
        List<Employee> deptTree = new ArrayList<>();
        // 遍历部门集合
        for (Employee employee : employees) {
            // 找出上级部门为空的部门，装进部门树
            if (employee.getParentId() == null) {
                deptTree.add(employee);
            } else {
                // 找出当前部门的上级部门
                Employee employeeParent = map.get(employee.getParentId());
                // 将当前部门装进上级部门的子部门集合里
                employeeParent.getChildren().add(employee);
            }
        }
        // 返回部门树
        return deptTree;
    }*/
}