package org.yjhking.pethome.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Permission extends BaseDomain {
    private String name;

    private String url;

    private String descs;

    private String sn;
}