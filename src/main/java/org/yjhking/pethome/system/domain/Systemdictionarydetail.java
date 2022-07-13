package org.yjhking.pethome.system.domain;

import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
public class Systemdictionarydetail extends BaseDomain {
    private String name;
    
    private Long typesId;
    
    private Systemdictionarytype systemdictionarytype;
}