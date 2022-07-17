package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.query.DepartmentQuery;
import org.yjhking.pethome.org.service.DepartmentService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Department> findAll() {
        return departmentService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        return departmentService.selectByPrimaryKey(id);
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
            departmentService.deleteByPrimaryKey(id);
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
     * @param department 修改内容
     * @param id         修改id
     * @return 返回信息
     */
    @PutMapping("/{id}")
    public AjaxResult editById(@RequestBody Department department, @PathVariable Long id) {
        department.setId(id);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            departmentService.updateByPrimaryKeySelective(department);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("修改失败");
        }
        return ajaxResult;
    }
    
    /**
     * 增加
     *
     * @param department 增加内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Department department) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            departmentService.insertSelective(department);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("新增失败");
        }
        return ajaxResult;
    }
    
    /**
     * 高级分页查询
     *
     * @param departmentQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Department> queryPage(@RequestBody DepartmentQuery departmentQuery) {
        return departmentService.queryData(departmentQuery);
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
            departmentService.patchDelete(ids);
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
    @GetMapping("/deptTree")
    public List<Department> deptTree() {
        return departmentService.deptTree();
    }
}
