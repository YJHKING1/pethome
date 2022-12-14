package org.yjhking.pethome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.user.domain.Wxuser;
import org.yjhking.pethome.user.query.WxuserQuery;
import org.yjhking.pethome.user.service.WxuserService;

import java.util.List;

/**
 * (t_wxwxuser)表控制层
 *
 * @author YJH
 */
@RestController
@RequestMapping("/wxwxuser")
public class WxuserController {
    @Autowired
    private WxuserService wxuserService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Wxuser> findAll() {
        return wxuserService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Wxuser findById(@PathVariable Long id) {
        return wxuserService.selectByPrimaryKey(id);
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
            wxuserService.deleteByPrimaryKey(id);
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
     * @param wxuser 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Wxuser wxuser) {
        AjaxResult ajaxResult = new AjaxResult();
        if (wxuser.getId() == null) {
            try {
                wxuserService.insertSelective(wxuser);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                wxuserService.updateByPrimaryKeySelective(wxuser);
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
     * @param wxuserQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Wxuser> queryPage(@RequestBody WxuserQuery wxuserQuery) {
        return wxuserService.queryData(wxuserQuery);
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
            wxuserService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
