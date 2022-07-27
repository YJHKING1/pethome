package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderAddress;
import org.yjhking.pethome.order.query.OrderAddressQuery;
import org.yjhking.pethome.order.service.OrderAddressService;

import java.util.List;

/**
 * (t_order_address)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/orderAddress")
public class OrderAddressController {
    @Autowired
    private OrderAddressService orderAddressService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderAddress> findAll() {
        return orderAddressService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderAddress findById(@PathVariable Long id) {
        return orderAddressService.selectByPrimaryKey(id);
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
            orderAddressService.deleteByPrimaryKey(id);
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
     * @param OrderAddress 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderAddress OrderAddress) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderAddress.getId() == null) {
            try {
                orderAddressService.insertSelective(OrderAddress);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderAddressService.updateByPrimaryKeySelective(OrderAddress);
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
     * @param OrderAddressQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderAddress> queryPage(@RequestBody OrderAddressQuery OrderAddressQuery) {
        return orderAddressService.queryData(OrderAddressQuery);
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
            orderAddressService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}