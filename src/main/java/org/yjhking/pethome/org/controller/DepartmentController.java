package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.DepartmentQuery;
import org.yjhking.pethome.org.service.DepartmentService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/Department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    
    @GetMapping
    public List<Department> findAll() {
        return departmentService.selectAll();
    }
    
    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        return departmentService.selectByPrimaryKey(id);
    }
    
    @DeleteMapping("/{id}")
    public Long deleteById(@PathVariable Long id) {
        return departmentService.deleteByPrimaryKey(id);
    }
    
    @PutMapping("/{id}")
    public AjaxResult editById(@RequestBody Department department, @PathVariable Long id) {
        department.setId(id);
        departmentService.updateByPrimaryKeySelective(department);
        return new AjaxResult();
    }
    
    @PutMapping
    public AjaxResult add(@RequestBody Department department) {
        Long id = departmentService.insertSelective(department);
        return new AjaxResult();
    }
    
    @PostMapping
    public PageList<Department> queryPage(@RequestBody DepartmentQuery departmentQuery) {
        return departmentService.queryData(departmentQuery);
    }
}
