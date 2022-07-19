package org.yjhking.pethome.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Menu extends BaseDomain {
    private String name;

    private String component;

    private String url;

    private String icon;

    private Integer index;

    private Long parentId;

    private String intro;

    private Boolean state;
}