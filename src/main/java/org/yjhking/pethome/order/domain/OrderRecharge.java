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
public class OrderRecharge extends BaseDomain {
    private String digest;

    private Integer state;

    private Long money;

    private String ordersn;

    private String paysn;

    private Date lastpaytime;

    private Date lastconfirmtime;

    private Long userId;
}