package org.yjhking.pethome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.user.query.LogininfoQuery;
import org.yjhking.pethome.user.service.LogininfoService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/logininfo")
public class LogininfoController {
    @Autowired
    private LogininfoService logininfoService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Logininfo> findAll() {
        return logininfoService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Logininfo findById(@PathVariable Long id) {
        return logininfoService.selectByPrimaryKey(id);
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
            logininfoService.deleteByPrimaryKey(id);
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
     * @param logininfo 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Logininfo logininfo) {
        AjaxResult ajaxResult = new AjaxResult();
        if (logininfo.getId() == null) {
            try {
                logininfoService.insertSelective(logininfo);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                logininfoService.updateByPrimaryKeySelective(logininfo);
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
     * @param logininfoQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Logininfo> queryPage(@RequestBody LogininfoQuery logininfoQuery) {
        return logininfoService.queryData(logininfoQuery);
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
            logininfoService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
