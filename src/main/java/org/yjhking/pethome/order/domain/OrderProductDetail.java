package org.yjhking.pethome.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OrderProductDetail extends BaseDomain {
    private String name;

    private String resources;

    private BigDecimal saleprice;

    private Date offsaletime;

    private Date onsaletime;

    private Long state;

    private String costprice;

    private Date createtime;

    private Long salecount;

    private Long productId;

    private Long orderId;
}