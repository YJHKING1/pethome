package org.yjhking.pethome.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.system.domain.Role;
import org.yjhking.pethome.system.mapper.RoleMapper;
import org.yjhking.pethome.system.service.RoleService;

import java.util.List;

/**
 * @author YJH
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    @Transactional
    public Integer deleteByPrimaryKey(Long id) {
        roleMapper.deleteMenuByRoleId(id);
        roleMapper.deletePerMissionByRoleId(id);
        roleMapper.deleteEmployeeByRoleId(id);
        return super.deleteByPrimaryKey(id);
    }
    
    @Override
    @Transactional
    public Integer insertSelective(Role role) {
        super.insertSelective(role);
        return insertMenuPermission(role);
    }
    
    @Override
    @Transactional
    public Integer updateByPrimaryKeySelective(Role role) {
        super.updateByPrimaryKeySelective(role);
        roleMapper.deleteMenuByRoleId(role.getId());
        roleMapper.deletePerMissionByRoleId(role.getId());
        return insertMenuPermission(role);
    }
    
    @Override
    public void patchDelete(List<Long> ids) {
        for (Long id : ids) {
            deleteByPrimaryKey(id);
        }
    }
    
    private Integer insertMenuPermission(Role role) {
        List<Long> menus = role.getMenus();
        if (menus.size() > 0) {
            roleMapper.insertRoleMenu(role);
        }
        List<Long> permissions = role.getPermissions();
        if (permissions.size() > 0) {
            roleMapper.insertRolePermission(role);
        }
        return null;
    }
}
