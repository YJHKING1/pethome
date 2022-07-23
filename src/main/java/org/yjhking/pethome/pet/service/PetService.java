package org.yjhking.pethome.pet.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.PetType;

import java.util.List;

/**
 * @author YJH
 */
public interface PetService extends BaseService<Pet> {
    List<PetType> typeTree();
}
