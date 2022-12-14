package org.yjhking.pethome.pet.query;

import lombok.Data;
import org.yjhking.pethome.basic.query.BaseQuery;

/**
 * 店铺查询工具类
 *
 * @author YJH
 */
@Data
public class PetQuery extends BaseQuery {
    /**
     * 上下架状态
     */
    private Integer state;
    /**
     * 商店id
     */
    private Long shopId;
}
