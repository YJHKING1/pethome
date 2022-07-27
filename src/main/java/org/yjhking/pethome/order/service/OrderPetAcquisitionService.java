package org.yjhking.pethome.order.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.order.domain.OrderPetAcquisition;
import org.yjhking.pethome.order.dto.OrderDto;
import org.yjhking.pethome.order.query.OrderPetAcquisitionQuery;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YJH
 */
public interface OrderPetAcquisitionService extends BaseService<OrderPetAcquisition> {
    PageList<OrderPetAcquisition> queryPage(OrderPetAcquisitionQuery query, HttpServletRequest request);
    
    void confirm(OrderDto orderDto);
}
