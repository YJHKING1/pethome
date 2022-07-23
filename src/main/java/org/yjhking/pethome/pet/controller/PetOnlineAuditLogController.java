package org.yjhking.pethome.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.PetOnlineAuditLog;
import org.yjhking.pethome.pet.query.PetOnlineAuditLogQuery;
import org.yjhking.pethome.pet.service.PetOnlineAuditLogService;

import java.util.List;

/**
 * (t_petOnlineAuditLog_online_audit_log)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/petOnlineAuditLog")
public class PetOnlineAuditLogController {
    @Autowired
    private PetOnlineAuditLogService petOnlineAuditLogService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<PetOnlineAuditLog> findAll() {
        return petOnlineAuditLogService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public PetOnlineAuditLog findById(@PathVariable Long id) {
        return petOnlineAuditLogService.selectByPrimaryKey(id);
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
            petOnlineAuditLogService.deleteByPrimaryKey(id);
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
     * @param PetOnlineAuditLog 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody PetOnlineAuditLog PetOnlineAuditLog) {
        AjaxResult ajaxResult = new AjaxResult();
        if (PetOnlineAuditLog.getId() == null) {
            try {
                petOnlineAuditLogService.insertSelective(PetOnlineAuditLog);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                petOnlineAuditLogService.updateByPrimaryKeySelective(PetOnlineAuditLog);
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
     * @param PetOnlineAuditLogQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<PetOnlineAuditLog> queryPage(@RequestBody PetOnlineAuditLogQuery PetOnlineAuditLogQuery) {
        return petOnlineAuditLogService.queryData(PetOnlineAuditLogQuery);
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
            petOnlineAuditLogService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
