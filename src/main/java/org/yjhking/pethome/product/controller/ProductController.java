package org.yjhking.pethome.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.product.domain.Product;
import org.yjhking.pethome.product.query.ProductQuery;
import org.yjhking.pethome.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (t_product)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Product> findAll() {
        return productService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.selectByPrimaryKey(id);
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
            productService.deleteByPrimaryKey(id);
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
     * @param Product 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Product Product) {
        AjaxResult ajaxResult = new AjaxResult();
        if (Product.getId() == null) {
            try {
                productService.insertSelective(Product);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                productService.updateByPrimaryKeySelective(Product);
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
     * @param ProductQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Product> queryPage(@RequestBody ProductQuery ProductQuery) {
        return productService.queryData(ProductQuery);
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
            productService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 宠物上架
     *
     * @param ids     要上架的id集合
     * @param request 请求
     * @return 返回信息
     */
    @PostMapping("/onsale")
    public AjaxResult onsale(@RequestBody List<Long> ids, HttpServletRequest request) {
        try {
            return productService.onsale(ids, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "上架失败");
        }
    }
    
    /**
     * 宠物下架
     *
     * @param ids     要下架的id集合
     * @param request 请求
     * @return 返回信息
     */
    @PostMapping("/offsale")
    public AjaxResult offsale(@RequestBody List<Long> ids, HttpServletRequest request) {
        try {
            return productService.offsale(ids, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "下架失败");
        }
    }
}
