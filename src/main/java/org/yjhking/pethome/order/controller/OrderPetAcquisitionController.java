package org.yjhking.pethome.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderPetAcquisition;
import org.yjhking.pethome.order.dto.OrderDto;
import org.yjhking.pethome.order.query.OrderPetAcquisitionQuery;
import org.yjhking.pethome.order.service.OrderPetAcquisitionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (t_order_pet_acquisition)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/acquisitionOrder")
public class OrderPetAcquisitionController {
    @Autowired
    private OrderPetAcquisitionService orderPetAcquisitionService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<OrderPetAcquisition> findAll() {
        return orderPetAcquisitionService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public OrderPetAcquisition findById(@PathVariable Long id) {
        return orderPetAcquisitionService.selectByPrimaryKey(id);
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
            orderPetAcquisitionService.deleteByPrimaryKey(id);
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
     * @param OrderPetAcquisition 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody OrderPetAcquisition OrderPetAcquisition) {
        AjaxResult ajaxResult = new AjaxResult();
        if (OrderPetAcquisition.getId() == null) {
            try {
                orderPetAcquisitionService.insertSelective(OrderPetAcquisition);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                orderPetAcquisitionService.updateByPrimaryKeySelective(OrderPetAcquisition);
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
     * @param query 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<OrderPetAcquisition> queryPage(@RequestBody OrderPetAcquisitionQuery query, HttpServletRequest request) {
        return orderPetAcquisitionService.queryPage(query, request);
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
            orderPetAcquisitionService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 确认订单
     *
     * @param orderDto 订单信息
     * @return 返回信息
     */
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody OrderDto orderDto) {
        try {
            orderPetAcquisitionService.confirm(orderDto);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "确认订单失败");
        }
    }
}
