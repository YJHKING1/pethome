package org.yjhking.pethome.user.jwt;

import lombok.Data;
import org.yjhking.pethome.system.domain.Menu;
import org.yjhking.pethome.user.domain.Logininfo;

import java.util.List;

@Data
public class LoginData {
    /**
     * 当前登录人拥有的权限：保存的是sn
     */
    private List<String> permissions;
    /**
     * 当前登录人拥有的菜单
     */
    private List<Menu> menus;
    /**
     * 用户登录信息
     */
    private Logininfo logininfo;
}