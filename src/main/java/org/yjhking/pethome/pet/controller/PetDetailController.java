package org.yjhking.pethome.pet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.PetDetail;
import org.yjhking.pethome.pet.query.PetDetailQuery;
import org.yjhking.pethome.pet.service.PetDetailService;

import java.util.List;

/**
 * (t_PetDetail_detail)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/petDetail")
public class PetDetailController {
    @Autowired
    private PetDetailService petDetailService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<PetDetail> findAll() {
        return petDetailService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public PetDetail findById(@PathVariable Long id) {
        return petDetailService.selectByPrimaryKey(id);
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
            petDetailService.deleteByPrimaryKey(id);
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
     * @param PetDetail 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody PetDetail PetDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        if (PetDetail.getId() == null) {
            try {
                petDetailService.insertSelective(PetDetail);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                petDetailService.updateByPrimaryKeySelective(PetDetail);
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
     * @param PetDetailQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<PetDetail> queryPage(@RequestBody PetDetailQuery PetDetailQuery) {
        return petDetailService.queryData(PetDetailQuery);
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
            petDetailService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
