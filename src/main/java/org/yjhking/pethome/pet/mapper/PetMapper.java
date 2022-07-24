package org.yjhking.pethome.pet.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.pet.domain.Pet;

import java.util.Map;

/**
 * @author YJH
 */
public interface PetMapper extends BaseMapper<Pet> {
    void onsale(Map<String, Object> params);
    
    void offsale(Map<String, Object> params);
}