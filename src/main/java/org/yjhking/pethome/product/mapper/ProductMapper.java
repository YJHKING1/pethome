package org.yjhking.pethome.product.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.product.domain.Product;

import java.util.Map;

/**
 * @author YJH
 */
public interface ProductMapper extends BaseMapper<Product> {
    void onsale(Map<String, Object> params);
    
    void offsale(Map<String, Object> params);
}