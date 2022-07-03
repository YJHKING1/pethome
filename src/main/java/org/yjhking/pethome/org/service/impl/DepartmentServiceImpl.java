package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.mapper.DepartmentMapper;
import org.yjhking.pethome.org.query.DepartmentQuery;
import org.yjhking.pethome.org.service.DepartmentService;

import java.util.List;

/**
 * 部门业务层
 *
 * @author YJH
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Transactional
    @Override
    public Long deleteByPrimaryKey(Long id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long insertSelective(Department department) {
        return departmentMapper.insertSelective(department);
    }
    
    @Override
    public Department selectByPrimaryKey(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long updateByPrimaryKeySelective(Department department) {
        return departmentMapper.updateByPrimaryKeySelective(department);
    }
    
    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }
    
    @Override
    public PageList<Department> queryData(DepartmentQuery departmentQuery) {
        // 查询总条数
        Long queryCount = departmentMapper.queryCount(departmentQuery);
        // 如果查询总条数为0，则直接返回空集合
        if (queryCount < 1) {
            return new PageList<Department>();
        }
        // 查询结果
        List<Department> departments = departmentMapper.queryData(departmentQuery);
        return new PageList<Department>(queryCount, departments);
    }
}