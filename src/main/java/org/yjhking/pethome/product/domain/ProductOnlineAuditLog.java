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
public class ProductOnlineAuditLog extends BaseDomain {
    private Long productId;

    private Byte state;

    private Long auditId;

    private Date auditTime;

    private String note;
}