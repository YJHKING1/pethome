package org.yjhking.pethome.order.query;

import lombok.Data;
import org.yjhking.pethome.basic.query.BaseQuery;

/**
 * 店铺查询工具类
 *
 * @author YJH
 */
@Data
public class OrderPetAcquisitionQuery extends BaseQuery {
    private Long employeeId;
    private Long shopId;
}
