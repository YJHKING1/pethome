package org.yjhking.pethome.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.product.domain.ProductCoupons;
import org.yjhking.pethome.product.query.ProductCouponsQuery;
import org.yjhking.pethome.product.service.ProductCouponsService;

import java.util.List;

/**
 * (t_product_coupons)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/productCoupons")
public class ProductCouponsController {
    @Autowired
    private ProductCouponsService productCouponsService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<ProductCoupons> findAll() {
        return productCouponsService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public ProductCoupons findById(@PathVariable Long id) {
        return productCouponsService.selectByPrimaryKey(id);
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
            productCouponsService.deleteByPrimaryKey(id);
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
     * @param ProductCoupons 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody ProductCoupons ProductCoupons) {
        AjaxResult ajaxResult = new AjaxResult();
        if (ProductCoupons.getId() == null) {
            try {
                productCouponsService.insertSelective(ProductCoupons);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                productCouponsService.updateByPrimaryKeySelective(ProductCoupons);
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
     * @param ProductCouponsQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<ProductCoupons> queryPage(@RequestBody ProductCouponsQuery ProductCouponsQuery) {
        return productCouponsService.queryData(ProductCouponsQuery);
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
            productCouponsService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
