package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.org.service.ShopService;

import java.util.Date;

/**
 * @author YJH
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    
    @Override
    public Integer insertSelective(Shop shop) {
        shop.setRegisterTime(new Date());
        return shopMapper.insertSelective(shop);
    }
}
