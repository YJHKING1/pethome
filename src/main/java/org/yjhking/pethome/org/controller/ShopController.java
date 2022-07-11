package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.ShopQuery;
import org.yjhking.pethome.org.service.ShopService;

import java.util.List;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Shop> findAll() {
        return shopService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Shop findById(@PathVariable Long id) {
        return shopService.selectByPrimaryKey(id);
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
            shopService.deleteByPrimaryKey(id);
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
     * @param shop 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Shop shop) {
        AjaxResult ajaxResult = new AjaxResult();
        if (shop.getId() == null) {
            try {
                shopService.insertSelective(shop);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                shopService.updateByPrimaryKeySelective(shop);
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
     * @param shopQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Shop> queryPage(@RequestBody ShopQuery shopQuery) {
        return shopService.queryData(shopQuery);
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
            shopService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 店铺入驻
     *
     * @param shop 店铺信息
     * @return 返回信息
     */
    @PostMapping("/settlement")
    public AjaxResult settlement(@RequestBody Shop shop) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            shopService.settlement(shop);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("系统错误");
        }
        return ajaxResult;
    }
}
