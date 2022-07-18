package org.yjhking.pethome.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.user.domain.Wxuser;

/**
 * @author YJH
 */
public interface WxuserMapper extends BaseMapper<Wxuser> {
    Wxuser selectByOpenid(@Param("openid") String openid);
}