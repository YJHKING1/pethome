package org.yjhking.pethome.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.system.domain.Menu;
import org.yjhking.pethome.system.mapper.MenuMapper;
import org.yjhking.pethome.system.service.MenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author YJH
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    
    @Override
    public List<Menu> menuTree() {
        // 查询所有菜单
        List<Menu> menus = menuMapper.selectAll();
        // 创建Map，装入id和菜单对象
        HashMap<Long, Menu> map = new HashMap<>();
        // 将所有菜单对象装进Map中
        for (Menu menu : menus) {
            map.put(menu.getId(), menu);
        }
        // 创建List，装入要返回的菜单树
        List<Menu> menuTrees = new ArrayList<>();
        // 遍历所有菜单集合
        for (Menu menu : menus) {
            // 如果菜单的父id为空，则说明是一级菜单，直接添加到返回结果中
            if (menu.getParentId() == null) {
                // 将顶级菜单装入返回集合
                menuTrees.add(menu);
            } else {
                // 如果菜单的父id不为空，则查找父菜单，将子菜单添加到父菜单的子菜单集合中
                Menu parentMenu = map.get(menu.getParentId());
                parentMenu.getChildren().add(menu);
            }
        }
        // 返回菜单树集合
        return menuTrees;
    }
}
