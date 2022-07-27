package org.yjhking.pethome.order.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OrderGoods extends BaseDomain {
    private String digest;

    private Integer state;

    private Long price;

    private String ordersn;

    private String paysn;

    private Date lastpaytime;

    private Date lastconfirmtime;

    private Long goodsId;

    private Long userId;

    private Long shopId;

    private Long addressId;
}