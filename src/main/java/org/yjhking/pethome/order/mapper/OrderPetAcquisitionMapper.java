package org.yjhking.pethome.order.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.order.domain.OrderPetAcquisition;

/**
 * @author YJH
 */
public interface OrderPetAcquisitionMapper extends BaseMapper<OrderPetAcquisition> {
    OrderPetAcquisition selectByMsgId(Long msgId);
}