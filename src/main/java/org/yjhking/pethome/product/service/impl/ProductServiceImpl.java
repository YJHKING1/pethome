package org.yjhking.pethome.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yjhking.pethome.basic.constants.FastDfsImgConstants;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.BaiduAiUtils;
import org.yjhking.pethome.basic.util.LoginContext;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.product.domain.Product;
import org.yjhking.pethome.product.domain.ProductDetail;
import org.yjhking.pethome.product.domain.ProductOnlineAuditLog;
import org.yjhking.pethome.product.mapper.ProductDetailMapper;
import org.yjhking.pethome.product.mapper.ProductMapper;
import org.yjhking.pethome.product.mapper.ProductOnlineAuditLogMapper;
import org.yjhking.pethome.product.service.ProductService;
import org.yjhking.pethome.user.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YJH
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ProductOnlineAuditLogMapper productOnlineAuditLogMapper;
    
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        productDetailMapper.deleteByProId(id);
        return super.deleteByPrimaryKey(id);
    }
    
    @Override
    public Integer insertSelective(Product product) {
        super.insertSelective(product);
        ProductDetail productDetail = product.getProductDetail();
        if (productDetail != null) {
            productDetail.setProductId(product.getId());
            productDetailMapper.insertSelective(productDetail);
        }
        return null;
    }
    
    @Override
    public Integer updateByPrimaryKeySelective(Product product) {
        super.updateByPrimaryKeySelective(product);
        ProductDetail productDetail = product.getProductDetail();
        if (productDetail != null) {
            productDetail.setProductId(product.getId());
            productDetailMapper.updateByPrimaryKeySelective(productDetail);
        }
        return null;
    }
    
    @Override
    public void patchDelete(List<Long> ids) {
        productDetailMapper.patchDeleteByProIds(ids);
        super.patchDelete(ids);
    }
    
    @Override
    public AjaxResult onsale(List<Long> ids, HttpServletRequest request) {
        // 上架 -不能用批量操作，有上架审核
        for (Long id : ids) {
            // 上架自动审核文本
            Product product = productMapper.selectByPrimaryKey(id);
            String auditText = product.getName();
            Boolean textBoo = BaiduAiUtils.textCensor(auditText);
            // 审核图片：多张图片resources
            Boolean imageBoo = true;
            // 图片途径是否为空
            if (StringUtils.hasLength(product.getResources())) {
                // 判断是否有多张图片
                if (product.getResources().contains(",")) {
                    String[] resources = product.getResources().split(",");
                    for (String resource : resources) {
                        String petResources = FastDfsImgConstants.IMG_SERVER_PREFIX_URL + resource;
                        imageBoo = BaiduAiUtils.imgCensor(petResources);
                        if (!imageBoo) {
                            break;
                        }
                    }
                } else {
                    String petResources = FastDfsImgConstants.IMG_SERVER_PREFIX_URL + product.getResources();
                    imageBoo = BaiduAiUtils.imgCensor(petResources);
                }
            }
            // 获取审核人
            Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
            Employee employee = employeeMapper.selectByLogininfoId(logininfo.getId());
            
            // 审核通过
            if (textBoo && imageBoo) {
                // 数据库操作
                Map<String, Object> params = new HashMap<>();
                params.put("id", id);
                params.put("onsaletime", new Date());
                productMapper.onsale(params);
                // 审核成功-note
                // 记录审核日志
                ProductOnlineAuditLog productOnlineAuditLog = new ProductOnlineAuditLog();
                productOnlineAuditLog.setState((byte) 1);
                productOnlineAuditLog.setAuditId(id);
                productOnlineAuditLog.setAuditId(employee.getId());
                productOnlineAuditLog.setNote("审核成功！");
                productOnlineAuditLogMapper.insertSelective(productOnlineAuditLog);
            } else {
                // 审核失败-note-当前是下架状态，如果审核失败！
                // 记录审核日志
                ProductOnlineAuditLog productOnlineAuditLog = new ProductOnlineAuditLog();
                productOnlineAuditLog.setState((byte) 0);
                productOnlineAuditLog.setAuditId(id);
                productOnlineAuditLog.setAuditId(employee.getId());
                productOnlineAuditLog.setNote("审核失败,宠物名称或图片不合法!!!");
                productOnlineAuditLogMapper.insertSelective(productOnlineAuditLog);
                //审核失败，上架失败
                return new AjaxResult(false, "上架失败！审核失败,宠物名称或图片不合法!!!");
            }
        }
        return AjaxResult.me();
    }
    
    @Override
    public AjaxResult offsale(List<Long> ids, HttpServletRequest request) {
        // 下架：修改state为0，offsaletime为当前时间 ,可以用批量操作
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("offsaletime", new Date());
        productMapper.offsale(params);
        return AjaxResult.me();
    }
}
