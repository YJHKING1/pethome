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
public class OrderPetAcquisition extends BaseDomain {
    private String ordersn;

    private String digest;

    private Date lastcomfirmtime;

    private Integer state;

    private Long price;

    private Integer paytype;

    private Long petId;

    private Long userId;

    private Long shopId;

    private String address;

    private Long empId;

    private Long searchMasterMsgId;
}