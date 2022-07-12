package org.yjhking.pethome.org.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ShopAuditLog extends BaseDomain {
    private Integer state;

    private Long shopId;

    private Long auditId;

    private Date auditTime;

    private String note;
}