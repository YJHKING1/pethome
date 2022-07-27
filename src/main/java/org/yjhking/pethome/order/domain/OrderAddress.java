package org.yjhking.pethome.order.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
/**
    * 订单地址
    */
@Data
@EqualsAndHashCode(callSuper=true)
public class OrderAddress extends BaseDomain {
    private Date createtime;

    private Date updatetime;

    private Long orderId;

    private String ordersn;

    private String contacts;

    private String areacode;

    private String address;

    private String fulladdress;

    private String phone;

    private String phoneback;

    private String tel;

    private String postcode;

    private String email;
}