package org.yjhking.pethome.product.service;

import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.product.domain.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author YJH
 */
public interface ProductService extends BaseService<Product> {
    AjaxResult onsale(List<Long> ids, HttpServletRequest request);
    
    AjaxResult offsale(List<Long> ids, HttpServletRequest request);
}
