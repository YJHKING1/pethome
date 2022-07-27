package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderRecharge;
import org.yjhking.pethome.order.query.OrderRechargeQuery;
import org.yjhking.pethome.order.service.OrderRechargeService;

import java.util.List;

/**
 * (t_order_recharge)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/rechargeOrder")
public class OrderRechargeController {
    @Autowired
    private OrderRechargeService orderRechargeService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderRecharge> findAll() {
        return orderRechargeService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderRecharge findById(@PathVariable Long id) {
        return orderRechargeService.selectByPrimaryKey(id);
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
            orderRechargeService.deleteByPrimaryKey(id);
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
     * @param OrderRecharge 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderRecharge OrderRecharge) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderRecharge.getId() == null) {
            try {
                orderRechargeService.insertSelective(OrderRecharge);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderRechargeService.updateByPrimaryKeySelective(OrderRecharge);
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
     * @param OrderRechargeQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderRecharge> queryPage(@RequestBody OrderRechargeQuery OrderRechargeQuery) {
        return orderRechargeService.queryData(OrderRechargeQuery);
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
            orderRechargeService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
