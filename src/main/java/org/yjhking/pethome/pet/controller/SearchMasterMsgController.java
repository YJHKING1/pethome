package org.yjhking.pethome.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;
import org.yjhking.pethome.pet.query.SearchMasterMsgQuery;
import org.yjhking.pethome.pet.service.SearchMasterMsgService;

import java.util.List;

/**
 * (t_search_master_msg)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/searchMasterMsg")
public class SearchMasterMsgController {
    @Autowired
    private SearchMasterMsgService searchMasterMsgService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<SearchMasterMsg> findAll() {
        return searchMasterMsgService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public SearchMasterMsg findById(@PathVariable Long id) {
        return searchMasterMsgService.selectByPrimaryKey(id);
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
            searchMasterMsgService.deleteByPrimaryKey(id);
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
     * @param SearchMasterMsg 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody SearchMasterMsg SearchMasterMsg) {
        AjaxResult ajaxResult = new AjaxResult();
        if (SearchMasterMsg.getId() == null) {
            try {
                searchMasterMsgService.insertSelective(SearchMasterMsg);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                searchMasterMsgService.updateByPrimaryKeySelective(SearchMasterMsg);
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
     * @param SearchMasterMsgQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<SearchMasterMsg> queryPage(@RequestBody SearchMasterMsgQuery SearchMasterMsgQuery) {
        return searchMasterMsgService.queryData(SearchMasterMsgQuery);
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
            searchMasterMsgService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
}
