package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.dto.ShopDto;
import org.yjhking.pethome.org.mapper.ShopMapper;
import org.yjhking.pethome.org.service.ShopService;

import java.util.List;

/**
 * @author YJH
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Override
    public List<ShopDto> getCountByState() {
        return shopMapper.getCountByState();
    }
    
    @Transactional
    @Override
    public void patchInsert(List<Shop> shops) {
        shopMapper.patchInsert(shops);
    }
}
