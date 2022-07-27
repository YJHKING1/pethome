package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderAdopt;
import org.yjhking.pethome.order.query.OrderAdoptQuery;
import org.yjhking.pethome.order.service.OrderAdoptService;

import java.util.List;

/**
 * (t_order_adopt)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/adoptOrder")
public class OrderAdoptController {
    @Autowired
    private OrderAdoptService orderAdoptService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderAdopt> findAll() {
        return orderAdoptService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderAdopt findById(@PathVariable Long id) {
        return orderAdoptService.selectByPrimaryKey(id);
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
            orderAdoptService.deleteByPrimaryKey(id);
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
     * @param OrderAdopt 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderAdopt OrderAdopt) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderAdopt.getId() == null) {
            try {
                orderAdoptService.insertSelective(OrderAdopt);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderAdoptService.updateByPrimaryKeySelective(OrderAdopt);
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
     * @param OrderAdoptQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderAdopt> queryPage(@RequestBody OrderAdoptQuery OrderAdoptQuery) {
        return orderAdoptService.queryData(OrderAdoptQuery);
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
            orderAdoptService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
}
