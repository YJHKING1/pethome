package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.dto.ShopDto;

import java.util.List;

/**
 * @author YJH
 */
public interface ShopService extends BaseService<Shop> {
    List<ShopDto> getCountByState();
    
    void patchInsert(List<Shop> shops);
}
