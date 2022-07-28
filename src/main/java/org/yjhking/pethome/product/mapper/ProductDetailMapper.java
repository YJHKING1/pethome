package org.yjhking.pethome.product.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.product.domain.ProductDetail;

import java.util.List;

/**
 * @author YJH
 */
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    void deleteByProId(Long id);
    
    void patchDeleteByProIds(List<Long> ids);
}