package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderGoodsDetail;
import org.yjhking.pethome.order.query.OrderGoodsDetailQuery;
import org.yjhking.pethome.order.service.OrderGoodsDetailService;

import java.util.List;

/**
 * (t_order_goods_detail)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/orderGoodsDetail")
public class OrderGoodsDetailController {
    @Autowired
    private OrderGoodsDetailService orderGoodsDetailService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderGoodsDetail> findAll() {
        return orderGoodsDetailService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderGoodsDetail findById(@PathVariable Long id) {
        return orderGoodsDetailService.selectByPrimaryKey(id);
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
            orderGoodsDetailService.deleteByPrimaryKey(id);
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
     * @param OrderGoodsDetail 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderGoodsDetail OrderGoodsDetail) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderGoodsDetail.getId() == null) {
            try {
                orderGoodsDetailService.insertSelective(OrderGoodsDetail);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderGoodsDetailService.updateByPrimaryKeySelective(OrderGoodsDetail);
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
     * @param OrderGoodsDetailQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderGoodsDetail> queryPage(@RequestBody OrderGoodsDetailQuery OrderGoodsDetailQuery) {
        return orderGoodsDetailService.queryData(OrderGoodsDetailQuery);
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
            orderGoodsDetailService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
