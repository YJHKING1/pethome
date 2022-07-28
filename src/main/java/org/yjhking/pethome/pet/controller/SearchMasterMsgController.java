package org.yjhking.pethome.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;
import org.yjhking.pethome.pet.query.SearchMasterMsgQuery;
import org.yjhking.pethome.pet.service.SearchMasterMsgService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    
    /**
     * 发布寻主消息
     *
     * @param searchMasterMsg 发布寻主消息内容
     * @param request         请求对象
     * @return 返回信息
     */
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody SearchMasterMsg searchMasterMsg, HttpServletRequest request) {
        try {
            return searchMasterMsgService.publish(searchMasterMsg, request);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(false, "发布失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "系统错误，稍后重试!!!");
        }
    }
    
    /**
     * 待审核或驳回：查询待审核或驳回的寻主消息 - 平台管理员才能查询 - 可以通过按钮权限限制，这里不做判断
     */
    @PostMapping("/toAudit")
    public PageList<SearchMasterMsg> toAudit(@RequestBody SearchMasterMsgQuery query) {
        query.setState(0);
        return searchMasterMsgService.queryData(query);
    }
    
    /**
     * 寻主池：查询审核通过还没有分配店铺或者被拒单的寻主消息 - state=1，ShopId为null
     */
    @PostMapping("/toSearchMasterPool")
    public PageList<SearchMasterMsg> toSearchMasterPool(@RequestBody SearchMasterMsgQuery query) {
        query.setState(1);
        //由于寻主池查询条件和待处理查询条件冲突，但是查询的结构不一样，所以寻主池业务需要重新写
        //查询state=1，shopId is null的寻主消息
        return searchMasterMsgService.queryPagePool(query);
    }
    
    /**
     * 已完成：查询已完成的寻主消息 - 平台管理员才能查询 - 可以通过按钮权限限制，这里不做判断
     */
    @PostMapping("/finish")
    public PageList<SearchMasterMsg> finish(@RequestBody SearchMasterMsgQuery query) {
        query.setState(2);
        return searchMasterMsgService.queryData(query);
    }
    
    /**
     * 用户寻主列表：用于登录之后再个人中心查询自己所有状态的寻主消息
     */
    @PostMapping("/user")
    public PageList<SearchMasterMsg> userSearchMasterMsg(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request) {
        return searchMasterMsgService.userSearchMasterMsg(query, request);
    }
    
    /**
     * 待处理：查询审核通过并已经分配了店铺的寻主消息 - state=1，ShopId不为null
     */
    @PostMapping("/toHandle")
    public PageList<SearchMasterMsg> toHandle(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request) {
        return searchMasterMsgService.toHandle(query, request);
    }
    
    /**
     * 拒单
     */
    @GetMapping("/reject/{id}")
    public AjaxResult reject(@PathVariable("id") Long id) {
        try {
            searchMasterMsgService.reject(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "拒单失败!");
        }
    }
    
    /**
     * 接单
     */
    @PostMapping("/accept")
    public AjaxResult accept(@RequestBody Map<String, Object> params) {
        try {
            searchMasterMsgService.accept(params);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "接单失败!");
        }
    }
}
