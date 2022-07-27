package org.yjhking.pethome.pet.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.SearchMasterMsg;
import org.yjhking.pethome.pet.query.SearchMasterMsgQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author YJH
 */
public interface SearchMasterMsgService extends BaseService<SearchMasterMsg> {
    void publish(SearchMasterMsg searchMasterMsg, HttpServletRequest request);
    
    PageList<SearchMasterMsg> userSearchMasterMsg(SearchMasterMsgQuery query, HttpServletRequest request);
    
    PageList<SearchMasterMsg> toHandle(SearchMasterMsgQuery query, HttpServletRequest request);
    
    void reject(Long id);
    
    void accept(Map<String, Object> params);
}
