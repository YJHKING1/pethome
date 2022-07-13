package org.yjhking.pethome.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.util.ExcelUtils;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.domain.ShopAuditLog;
import org.yjhking.pethome.org.dto.ShopDto;
import org.yjhking.pethome.org.query.AjaxResult;
import org.yjhking.pethome.org.query.ShopQuery;
import org.yjhking.pethome.org.service.ShopService;

import javax.servlet.http.HttpServletResponse;
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
    
    /**
     * 店铺审核通过
     *
     * @param log 店铺审核信息
     * @return 返回信息
     */
    @PostMapping("/audit/pass")
    public AjaxResult pass(@RequestBody ShopAuditLog log) {
        try {
            shopService.pass(log);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "审核失败");
        }
    }
    
    /**
     * 店铺审核驳回
     *
     * @param log 店铺审核信息
     * @return 返回信息
     */
    @PostMapping("/audit/reject")
    public AjaxResult reject(@RequestBody ShopAuditLog log) {
        try {
            shopService.reject(log);
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "驳回失败");
        }
    }
    
    /**
     * 店铺激活
     *
     * @param id 要激活的店铺id
     * @return 返回信息
     */
    @GetMapping("/active/{id}")
    public AjaxResult active(@PathVariable Long id, HttpServletResponse response) {
        try {
            shopService.active(id);
            response.sendRedirect("http://localhost:8081/#/activation");
            return new AjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "激活失败");
        }
    }
    
    /**
     * 导出数据
     *
     * @param response 响应对象
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            List<Shop> shops = shopService.selectAll();
            ExcelUtils.exportExcel(shops, null, "店铺信息", Shop.class, "shop.xls", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 导入数据
     *
     * @param file 导入的文件
     */
    @PostMapping("/importExcel")
    public void importExcel(@RequestPart("file") MultipartFile file) {
        try {
            // 导入数据
            List<Shop> shops = ExcelUtils.importExcel(file, 0, 1, Shop.class);
            // 打印测试
            shops.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 统计状态
     *
     * @return 统计结果
     */
    @GetMapping("/echarts")
    public List<ShopDto> echartsData() {
        return shopService.getCountByState();
    }
}
