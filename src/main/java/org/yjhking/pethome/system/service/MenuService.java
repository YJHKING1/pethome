package org.yjhking.pethome.system.service;

import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.system.domain.Menu;

import java.util.List;

/**
 * @author YJH
 */
public interface MenuService extends BaseService<Menu> {
    /**
     * 菜单树
     *
     * @return 菜单树
     */
    List<Menu> menuTree();
}
