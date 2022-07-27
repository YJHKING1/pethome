package org.yjhking.pethome.pet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.Date;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMasterMsgAuditLog extends BaseDomain {
    private Long msgId;
    
    private Byte state;
    
    private Long auditId;
    
    private Date auditTime = new Date();
    
    private String note;
}