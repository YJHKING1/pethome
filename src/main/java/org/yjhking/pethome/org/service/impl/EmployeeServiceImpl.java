package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
        encodePassword(employee, "");
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
        encodePassword(employee, null);
        Logininfo logininfo = employee2Logininfo(employee);
        // 查询员工的登录信息id
        Long logininfoId = employeeMapper.selectByPrimaryKey(employee.getId()).getLogininfoId();
        employee.setLogininfoId(logininfoId);
        logininfo.setId(logininfoId);
        logininfo.setDisable(null);
        logininfoMapper.updateByPrimaryKeySelective(logininfo);
        employeeMapper.deleteEmployeeRoleByEmployeeId(employee.getId());
        employeeMapper.insertEmployeeRole(employee);
        // 更新到数据库
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    private void encodePassword(Employee employee, String missingValue) {
        String rawPassword = employee.getPassword();
        if (!StringUtils.hasText(rawPassword)) {
            employee.setPassword(missingValue);
            employee.setSalt(missingValue);
            return;
        }
        String salt = StrUtils.getComplexRandomString(32);
        employee.setSalt(salt);
        employee.setPassword(Md5Utils.encrypByMd5(rawPassword + salt));
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
