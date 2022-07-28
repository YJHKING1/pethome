package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderProduct;
import org.yjhking.pethome.order.query.OrderProductQuery;
import org.yjhking.pethome.order.service.OrderProductService;

import java.util.List;

/**
 * (t_order_product)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/orderProduct")
public class OrderProductController {
    @Autowired
    private OrderProductService orderProductService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderProduct> findAll() {
        return orderProductService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderProduct findById(@PathVariable Long id) {
        return orderProductService.selectByPrimaryKey(id);
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
            orderProductService.deleteByPrimaryKey(id);
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
     * @param OrderProduct 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderProduct OrderProduct) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderProduct.getId() == null) {
            try {
                orderProductService.insertSelective(OrderProduct);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderProductService.updateByPrimaryKeySelective(OrderProduct);
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
     * @param OrderProductQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderProduct> queryPage(@RequestBody OrderProductQuery OrderProductQuery) {
        return orderProductService.queryData(OrderProductQuery);
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
            orderProductService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
