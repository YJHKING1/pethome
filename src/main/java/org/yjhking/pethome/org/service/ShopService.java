package org.yjhking.pethome.org.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.domain.ShopAuditLog;
import org.yjhking.pethome.org.dto.ShopDto;

import javax.mail.MessagingException;
import java.util.List;

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
    
    /**
     * 店铺审核通过
     *
     * @param log 店铺审核信息
     */
    void pass(ShopAuditLog log) throws MessagingException;
    
    /**
     * 店铺审核驳回
     *
     * @param log 店铺审核信息
     */
    void reject(ShopAuditLog log) throws MessagingException;
    
    /**
     * 店铺激活
     *
     * @param id 要激活的店铺id
     */
    void active(Long id);
    
    List<ShopDto> getCountByState();
    
    void patchInsert(List<Shop> shops);
}
