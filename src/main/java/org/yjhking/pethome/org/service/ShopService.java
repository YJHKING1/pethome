package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.org.domain.Shop;

/**
 * @author YJH
 */
public interface ShopService extends BaseService<Shop> {
    /**
     * 店铺入驻
     *
     * @param shop 店铺信息
     */
    void settlement(Shop shop);
}
