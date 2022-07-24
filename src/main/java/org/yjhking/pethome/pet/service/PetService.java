package org.yjhking.pethome.pet.service;

import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.PetType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author YJH
 */
public interface PetService extends BaseService<Pet> {
    List<PetType> typeTree();
    
    AjaxResult onsale(List<Long> ids, HttpServletRequest request);
    
    AjaxResult offsale(List<Long> ids, HttpServletRequest request);
}
