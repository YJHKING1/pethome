package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.EmployeeQuery;
import org.yjhking.pethome.org.service.EmployeeService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Employee> findAll() {
        return employeeService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeService.selectByPrimaryKey(id);
    }
    
    /**
     * 删除
     *
     * @param id 删除id
     * @return 返回信息
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 修改
     *
     * @param employee 修改内容
     * @param id       修改id
     * @return 返回信息
     */
    /*@PutMapping("/{id}")
    public AjaxResult editById(@RequestBody Employee employee, @PathVariable Long id) {
        employee.setId(id);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.updateByPrimaryKeySelective(employee);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("修改失败");
        }
        return ajaxResult;
    }*/
    
    /**
     * 增加
     *
     * @param employee 增加内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Employee employee) {
        AjaxResult ajaxResult = new AjaxResult();
        if (employee.getId() == null) {
            try {
                employeeService.insertSelective(employee);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                employeeService.updateByPrimaryKeySelective(employee);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("修改失败");
            }
        }
        
        return ajaxResult;
    }
    
    /**
     * 高级分页查询
     *
     * @param employeeQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Employee> queryPage(@RequestBody EmployeeQuery employeeQuery) {
        return employeeService.queryData(employeeQuery);
    }
    
    /**
     * 批量删除
     *
     * @param ids 要删除的id集合
     * @return 返回信息
     */
    @PatchMapping
    public AjaxResult patchDelete(@RequestBody List<Long> ids) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 部门树
     *
     * @return 部门树集合
     */
    /*@GetMapping("/deptTree")
    public List<Employee> deptTree() {
        return employeeService.deptTree();
    }*/
}