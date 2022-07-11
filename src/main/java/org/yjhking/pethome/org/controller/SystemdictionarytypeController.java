package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Systemdictionarytype;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.SystemdictionarytypeQuery;
import org.yjhking.pethome.org.service.SystemdictionarytypeService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/systemdictionarytype")
public class SystemdictionarytypeController {
    @Autowired
    private SystemdictionarytypeService systemdictionarytypeService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Systemdictionarytype> findAll() {
        return systemdictionarytypeService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Systemdictionarytype findById(@PathVariable Long id) {
        return systemdictionarytypeService.selectByPrimaryKey(id);
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
            systemdictionarytypeService.deleteByPrimaryKey(id);
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
     * @param systemdictionarytype 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Systemdictionarytype systemdictionarytype) {
        AjaxResult ajaxResult = new AjaxResult();
        if (systemdictionarytype.getId() == null) {
            try {
                systemdictionarytypeService.insertSelective(systemdictionarytype);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                systemdictionarytypeService.updateByPrimaryKeySelective(systemdictionarytype);
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
     * @param systemdictionarytypeQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Systemdictionarytype> queryPage(@RequestBody SystemdictionarytypeQuery systemdictionarytypeQuery) {
        return systemdictionarytypeService.queryData(systemdictionarytypeQuery);
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
            systemdictionarytypeService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
