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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 新增到数据库
        departmentMapper.insertSelective(department);
        // 获得新增的id
        Long id = department.getId();
        // 获得父id
        Long parentId = department.getParentId();
        // 如果此部门为最上级则直接拼接自己的id路径
        if (parentId == null) {
            department.setDirPath("/" + id);
        } else {
            // 否则，先获得父路径
            String parentDirPath = departmentMapper.selectByPrimaryKey(parentId).getDirPath();
            // 再拼接父路径加自己的id
            department.setDirPath(parentDirPath + "/" + id);
        }
        // 更新到数据库
        departmentMapper.updateByPrimaryKeySelective(department);
        // 返回新增的id
        return id;
    }
    
    @Override
    public Department selectByPrimaryKey(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Long updateByPrimaryKeySelective(Department department) {
        // 获得父id
        Long parentId = department.getParentId();
        // 如果此部门为最上级则直接拼接自己的id路径
        if (parentId == null) {
            department.setDirPath("/" + department.getId());
        } else {
            // 否则，先获得父路径
            String parentDirPath = departmentMapper.selectByPrimaryKey(parentId).getDirPath();
            if (parentDirPath.indexOf(department.getId().toString()) != -1) {
                throw new RuntimeException("上级部门不可是自己");
            }
            // 再拼接父路径加自己的id
            department.setDirPath(parentDirPath + "/" + department.getId());
        }
        // 更新到数据库
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
    
    @Override
    public void patchDelete(List<Long> ids) {
        departmentMapper.patchDelete(ids);
    }
    
    @Override
    public List<Department> deptTree() {
        // 查询所有部门，装进集合
        List<Department> departments = departmentMapper.selectAll();
        // 新建map，键为部门id，值为部门
        Map<Long, Department> map = new HashMap<>();
        // 遍历部门集合，装进map
        for (Department department : departments) {
            map.put(department.getId(), department);
        }
        // 新建部门树
        List<Department> deptTree = new ArrayList<>();
        // 遍历部门集合
        for (Department department : departments) {
            // 找出上级部门为空的部门，装进部门树
            if (department.getParentId() == null) {
                deptTree.add(department);
            } else {
                // 找出当前部门的上级部门
                Department departmentParent = map.get(department.getParentId());
                // 将当前部门装进上级部门的子部门集合里
                departmentParent.getChildren().add(department);
            }
        }
        // 返回部门树
        return deptTree;
    }
}