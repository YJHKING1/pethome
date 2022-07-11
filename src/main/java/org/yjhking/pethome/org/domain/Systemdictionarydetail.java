package org.yjhking.pethome.org.domain;

import lombok.Data;
import org.yjhking.pethome.basic.domain.BaseDomain;

@Data
public class Systemdictionarydetail extends BaseDomain {
    private String name;
    
    private Long typesId;
    
    private Systemdictionarytype systemdictionarytype;
}