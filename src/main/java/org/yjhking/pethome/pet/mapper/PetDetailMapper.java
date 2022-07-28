package org.yjhking.pethome.pet.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.pet.domain.PetDetail;

import java.util.List;

/**
 * @author YJH
 */
public interface PetDetailMapper extends BaseMapper<PetDetail> {
    void deleteByPetId(Long id);
    
    void patchDeleteByPetIds(List<Long> ids);
}