package org.yjhking.pethome.product.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductCoupons extends BaseDomain {
    private String sn;

    private Long productId;

    private String productname;

    private Long userId;

    private String username;

    private Long shopId;

    private String shopname;

    private Date startTime;

    private Date endTime;
}