package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Systemdictionarydetail;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.SystemdictionarydetailQuery;
import org.yjhking.pethome.org.service.SystemdictionarydetailService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/systemdictionarydetail")
public class SystemdictionarydetailController {
    @Autowired
    private SystemdictionarydetailService systemdictionarydetailService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Systemdictionarydetail> findAll() {
        return systemdictionarydetailService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Systemdictionarydetail findById(@PathVariable Long id) {
        return systemdictionarydetailService.selectByPrimaryKey(id);
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
            systemdictionarydetailService.deleteByPrimaryKey(id);
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
     * @param systemdictionarydetail 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Systemdictionarydetail systemdictionarydetail) {
        AjaxResult ajaxResult = new AjaxResult();
        if (systemdictionarydetail.getId() == null) {
            try {
                systemdictionarydetailService.insertSelective(systemdictionarydetail);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                systemdictionarydetailService.updateByPrimaryKeySelective(systemdictionarydetail);
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
     * @param systemdictionarydetailQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Systemdictionarydetail> queryPage(@RequestBody SystemdictionarydetailQuery systemdictionarydetailQuery) {
        return systemdictionarydetailService.queryData(systemdictionarydetailQuery);
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
            systemdictionarydetailService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
