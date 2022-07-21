package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.Md5Utils;
import org.yjhking.pethome.basic.util.StrUtils;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.org.service.EmployeeService;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.mapper.LogininfoMapper;

/**
 * 部门业务层
 *
 * @author YJH
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private LogininfoMapper logininfoMapper;
    
    @Transactional
    @Override
    public Integer insertSelective(Employee employee) {
        String salt = StrUtils.getComplexRandomString(32);
        String password = Md5Utils.encrypByMd5(employee.getPassword() + salt);
        employee.setSalt(salt);
        employee.setPassword(password);
        Logininfo logininfo = employee2Logininfo(employee);
        logininfoMapper.insertSelective(logininfo);
        employee.setLogininfoId(logininfo.getId());
        // 新增到数据库
        employeeMapper.insertSelective(employee);
        employeeMapper.insertEmployeeRole(employee);
        return null;
    }
    
    @Transactional
    @Override
    public Integer updateByPrimaryKeySelective(Employee employee) {
        String salt = StrUtils.getComplexRandomString(32);
        String password = Md5Utils.encrypByMd5(employee.getPassword() + salt);
        employee.setSalt(salt);
        employee.setPassword(password);
        Logininfo logininfo = employee2Logininfo(employee);
        // 查询员工的登录信息id
        Long logininfoId = employeeMapper.selectByPrimaryKey(employee.getId()).getLogininfoId();
        logininfo.setId(logininfoId);
        logininfoMapper.updateByPrimaryKeySelective(logininfo);
        employeeMapper.deleteEmployeeRoleByEmployeeId(employee.getId());
        employeeMapper.insertEmployeeRole(employee);
        // 更新到数据库
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    private Logininfo employee2Logininfo(Employee employee) {
        Logininfo logininfo = new Logininfo();
        BeanUtils.copyProperties(employee, logininfo);
        logininfo.setType(0);
        return logininfo;
    }
    
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        //删除登录信息表数据
        logininfoMapper.deleteByPrimaryKey(employee.getLogininfoId());
        employeeMapper.deleteEmployeeRoleByEmployeeId(id);
        return super.deleteByPrimaryKey(id);
    }
}