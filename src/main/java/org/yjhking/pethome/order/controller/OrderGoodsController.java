package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderGoods;
import org.yjhking.pethome.order.query.OrderGoodsQuery;
import org.yjhking.pethome.order.service.OrderGoodsService;

import java.util.List;

/**
 * (t_order_goods)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/orderGoods")
public class OrderGoodsController {
    @Autowired
    private OrderGoodsService orderGoodsService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderGoods> findAll() {
        return orderGoodsService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderGoods findById(@PathVariable Long id) {
        return orderGoodsService.selectByPrimaryKey(id);
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
            orderGoodsService.deleteByPrimaryKey(id);
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
     * @param OrderGoods 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderGoods OrderGoods) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderGoods.getId() == null) {
            try {
                orderGoodsService.insertSelective(OrderGoods);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderGoodsService.updateByPrimaryKeySelective(OrderGoods);
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
     * @param OrderGoodsQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderGoods> queryPage(@RequestBody OrderGoodsQuery OrderGoodsQuery) {
        return orderGoodsService.queryData(OrderGoodsQuery);
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
            orderGoodsService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
