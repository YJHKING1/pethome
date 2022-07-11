package org.yjhking.pethome.org.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.org.domain.Shop;

/**
 * @author YJH
 */
public interface ShopMapper extends BaseMapper<Shop> {
    Shop selectByNameAndAddress(@Param("name") String name, @Param("address") String address);
}