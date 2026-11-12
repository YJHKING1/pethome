package org.yjhking.pethome.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.annotation.PreAuthorize;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.system.domain.Permission;
import org.yjhking.pethome.system.query.PermissionQuery;
import org.yjhking.pethome.system.service.PermissionService;

import java.util.List;

/**
 * (t_permission)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @PreAuthorize(name = "查看权限", sn = "permission:list")
    @GetMapping
    public List<Permission> findAll() {
        return permissionService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @PreAuthorize(name = "查看权限", sn = "permission:list")
    @GetMapping("/{id}")
    public Permission findById(@PathVariable Long id) {
        return permissionService.selectByPrimaryKey(id);
    }
    
    /**
     * 删除
     *
     * @param id 删除id
     * @return 返回信息
     */
    @PreAuthorize(name = "删除权限", sn = "permission:delete")
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            permissionService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 增加及修改
     *
     * @param permission 增加或修改内容
     * @return 返回信息
     */
    @PreAuthorize(name = "保存权限", sn = "permission:save")
    @PutMapping
    public AjaxResult add(@RequestBody Permission permission) {
        AjaxResult ajaxResult = new AjaxResult();
        if (permission.getId() == null) {
            try {
                permissionService.insertSelective(permission);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                permissionService.updateByPrimaryKeySelective(permission);
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
     * @param permissionQuery 高级分页查询参数
     * @return 查询结果
     */
    @PreAuthorize(name = "查看权限", sn = "permission:list")
    @PostMapping
    public PageList<Permission> queryPage(@RequestBody PermissionQuery permissionQuery) {
        return permissionService.queryData(permissionQuery);
    }
    
    /**
     * 批量删除
     *
     * @param ids 要删除的id集合
     * @return 返回信息
     */
    @PreAuthorize(name = "删除权限", sn = "permission:delete")
    @PatchMapping
    public AjaxResult patchDelete(@RequestBody List<Long> ids) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            permissionService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
