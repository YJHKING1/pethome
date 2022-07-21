package org.yjhking.pethome.system.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.system.domain.Role;

/**
 * @author YJH
 */
public interface RoleMapper extends BaseMapper<Role> {
    void deleteMenuByRoleId(Long id);
    
    void deletePerMissionByRoleId(Long id);
    
    void deleteEmployeeByRoleId(Long id);
    
    void insertRoleMenu(Role role);
    
    void insertRolePermission(Role role);
}