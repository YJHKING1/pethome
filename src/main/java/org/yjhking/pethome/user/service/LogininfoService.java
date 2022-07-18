package org.yjhking.pethome.user.service;

import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.user.domain.Logininfo;
import org.yjhking.pethome.user.dto.LoginDto;

import java.util.Map;

/**
 * 登录业务层接口
 *
 * @author YJH
 */
public interface LogininfoService extends BaseService<Logininfo> {
    /**
     * 登录
     *
     * @param loginDto 登录信息
     * @return 返回信息
     */
    AjaxResult account(LoginDto loginDto);
    
    /**
     * 微信登录
     *
     * @param code 微信code
     * @return 返回信息
     */
    AjaxResult wechatLogin(String code);
    
    AjaxResult wechatBinder(Map<String, String> map);
}
