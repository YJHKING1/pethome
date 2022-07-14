package org.yjhking.pethome.org.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.org.domain.Shop;
import org.yjhking.pethome.org.dto.ShopDto;

import java.util.List;

/**
 * @author YJH
 */
public interface ShopMapper extends BaseMapper<Shop> {
    Shop selectByNameAndAddress(@Param("name") String name, @Param("address") String address);
    
    List<ShopDto> getCountByState();
    
    void patchInsert(@Param("list") List<Shop> list);
}