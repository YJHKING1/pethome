package org.yjhking.pethome.pet.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;

/**
 * @author YJH
 */
public interface SearchMasterMsgMapper extends BaseMapper<SearchMasterMsg> {
    void reject(Long id);
}