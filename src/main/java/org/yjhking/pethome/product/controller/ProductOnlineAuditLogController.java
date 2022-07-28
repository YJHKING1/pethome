package org.yjhking.pethome.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.product.domain.ProductOnlineAuditLog;
import org.yjhking.pethome.product.query.ProductOnlineAuditLogQuery;
import org.yjhking.pethome.product.service.ProductOnlineAuditLogService;

import java.util.List;

/**
 * (t_product_online_audit_log)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/productOnlineAuditLog")
public class ProductOnlineAuditLogController {
    @Autowired
    private ProductOnlineAuditLogService productOnlineAuditLogService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<ProductOnlineAuditLog> findAll() {
        return productOnlineAuditLogService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public ProductOnlineAuditLog findById(@PathVariable Long id) {
        return productOnlineAuditLogService.selectByPrimaryKey(id);
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
            productOnlineAuditLogService.deleteByPrimaryKey(id);
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
     * @param ProductOnlineAuditLog 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody ProductOnlineAuditLog ProductOnlineAuditLog) {
        AjaxResult ajaxResult = new AjaxResult();
        if (ProductOnlineAuditLog.getId() == null) {
            try {
                productOnlineAuditLogService.insertSelective(ProductOnlineAuditLog);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                productOnlineAuditLogService.updateByPrimaryKeySelective(ProductOnlineAuditLog);
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
     * @param ProductOnlineAuditLogQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<ProductOnlineAuditLog> queryPage(@RequestBody ProductOnlineAuditLogQuery ProductOnlineAuditLogQuery) {
        return productOnlineAuditLogService.queryData(ProductOnlineAuditLogQuery);
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
            productOnlineAuditLogService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
