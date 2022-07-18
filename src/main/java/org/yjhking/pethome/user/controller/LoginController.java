package org.yjhking.pethome.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.user.dto.LoginDto;
import org.yjhking.pethome.user.service.LogininfoService;

import java.util.Map;

/**
 * 登录控制层
 *
 * @author YJH
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LogininfoService logininfoService;
    
    /**
     * 登录
     *
     * @param loginDto 登录信息
     * @return 返回信息
     */
    @RequestMapping("/account")
    public AjaxResult account(@RequestBody LoginDto loginDto) {
        try {
            return logininfoService.account(loginDto);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "系统异常");
        }
    }
    
    /**
     * 微信登录
     *
     * @param map code
     * @return 返回信息
     */
    @PostMapping("/wechat")
    public AjaxResult wechatLogin(@RequestBody Map<String, String> map) {
        try {
            String code = map.get("code");
            return logininfoService.wechatLogin(code);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "系统异常");
        }
    }
    
    /**
     * 微信绑定
     *
     * @param map map
     * @return 返回信息
     */
    @PostMapping("/wechat/binder")
    public AjaxResult wechatBinder(@RequestBody Map<String, String> map) {
        try {
            return logininfoService.wechatBinder(map);
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            return new AjaxResult(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "系统异常");
        }
    }
}
