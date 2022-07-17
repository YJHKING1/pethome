package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.ShopAuditLog;
import org.yjhking.pethome.org.query.ShopAuditLogQuery;
import org.yjhking.pethome.org.service.ShopAuditLogService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/shopAuditLog")
public class ShopAuditLogController {
    @Autowired
    private ShopAuditLogService shopAuditLogService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<ShopAuditLog> findAll() {
        return shopAuditLogService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public ShopAuditLog findById(@PathVariable Long id) {
        return shopAuditLogService.selectByPrimaryKey(id);
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
            shopAuditLogService.deleteByPrimaryKey(id);
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
     * @param shopAuditLog 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody ShopAuditLog shopAuditLog) {
        AjaxResult ajaxResult = new AjaxResult();
        if (shopAuditLog.getId() == null) {
            try {
                shopAuditLogService.insertSelective(shopAuditLog);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                shopAuditLogService.updateByPrimaryKeySelective(shopAuditLog);
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
     * @param shopAuditLogQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<ShopAuditLog> queryPage(@RequestBody ShopAuditLogQuery shopAuditLogQuery) {
        return shopAuditLogService.queryData(shopAuditLogQuery);
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
            shopAuditLogService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
