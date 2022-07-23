package org.yjhking.pethome.pet.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.Date;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PetOnlineAuditLog extends BaseDomain {
    private Long petId;
    
    private Byte state;
    
    private Long auditId;
    
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date auditTime;
    
    private String note;
}