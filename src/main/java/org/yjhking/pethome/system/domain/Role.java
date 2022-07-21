package org.yjhking.pethome.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yjhking.pethome.basic.domain.BaseDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YJH
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseDomain {
    private String name;
    private String sn;
    /**
     * 菜单中间表id集合
     */
    private List<Long> menus = new ArrayList<>();
    /**
     * 权限中间表id集合
     */
    private List<Long> permissions = new ArrayList<>();
}