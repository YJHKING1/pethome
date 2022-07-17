package org.yjhking.pethome.user.mapper;

import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.dto.LoginDto;

/**
 * @author YJH
 */
public interface LogininfoMapper extends BaseMapper<Logininfo> {
    Logininfo selectByAccount(LoginDto loginDto);
}