package org.yjhking.pethome.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.SearchMasterMsgAuditLog;
import org.yjhking.pethome.pet.query.SearchMasterMsgAuditLogQuery;
import org.yjhking.pethome.pet.service.SearchMasterMsgAuditLogService;

import java.util.List;

/**
 * (t_search_master_msg_audit_log)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/searchMasterMsgAuditLog")
public class SearchMasterMsgAuditLogController {
    @Autowired
    private SearchMasterMsgAuditLogService petTypeService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<SearchMasterMsgAuditLog> findAll() {
        return petTypeService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public SearchMasterMsgAuditLog findById(@PathVariable Long id) {
        return petTypeService.selectByPrimaryKey(id);
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
            petTypeService.deleteByPrimaryKey(id);
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
     * @param SearchMasterMsgAuditLog 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody SearchMasterMsgAuditLog SearchMasterMsgAuditLog) {
        AjaxResult ajaxResult = new AjaxResult();
        if (SearchMasterMsgAuditLog.getId() == null) {
            try {
                petTypeService.insertSelective(SearchMasterMsgAuditLog);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                petTypeService.updateByPrimaryKeySelective(SearchMasterMsgAuditLog);
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
     * @param SearchMasterMsgAuditLogQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<SearchMasterMsgAuditLog> queryPage(@RequestBody SearchMasterMsgAuditLogQuery SearchMasterMsgAuditLogQuery) {
        return petTypeService.queryData(SearchMasterMsgAuditLogQuery);
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
            petTypeService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
