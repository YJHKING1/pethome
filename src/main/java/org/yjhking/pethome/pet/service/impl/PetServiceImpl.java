package org.yjhking.pethome.pet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.PetType;
import org.yjhking.pethome.pet.mapper.PetTypeMapper;
import org.yjhking.pethome.pet.service.PetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author YJH
 */
@Service
public class PetServiceImpl extends BaseServiceImpl<Pet> implements PetService {
    @Autowired
    private PetTypeMapper petTypeMapper;
    
    @Override
    public List<PetType> typeTree() {
        List<PetType> petTypes = petTypeMapper.selectAll();
        HashMap<Long, PetType> map = new HashMap<>();
        for (PetType petType : petTypes) {
            map.put(petType.getId(), petType);
        }
        List<PetType> petTypeTree = new ArrayList<>();
        for (PetType petType : petTypes) {
            if (petType.getPid() == null) {
                petTypeTree.add(petType);
            } else {
                PetType parentType = map.get(petType.getPid());
                parentType.getChildren().add(petType);
            }
        }
        return petTypeTree;
    }
}
