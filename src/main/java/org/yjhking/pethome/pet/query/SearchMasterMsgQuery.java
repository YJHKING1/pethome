package org.yjhking.pethome.pet.query;

import lombok.Data;
import org.yjhking.pethome.basic.query.BaseQuery;

/**
 * 店铺查询工具类
 *
 * @author YJH
 */
@Data
public class SearchMasterMsgQuery extends BaseQuery {
    private Long shopId;
    private Long userId;
    private Integer state;
}
