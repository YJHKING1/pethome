package org.yjhking.pethome.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.system.domain.Permission;

/**
 * @author YJH
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    Permission selectBySn(@Param("sn") String sn);
}