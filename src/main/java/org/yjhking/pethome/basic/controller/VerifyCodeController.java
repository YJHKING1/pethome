package org.yjhking.pethome.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjhking.pethome.basic.Exception.BusinessRuntimeException;
import org.yjhking.pethome.basic.service.VerifyService;
import org.yjhking.pethome.org.query.AjaxResult;

/**
 * @author YJH
 */
@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {
    @Autowired
    private VerifyService verifyService;
    
    @GetMapping("/image/{key}")
    public AjaxResult imageVerifyCode(@PathVariable("key") String key) {
        AjaxResult result = AjaxResult.me();
        try {
            // 调用业务层接口，获得图片验证码
            result.setResultObj(verifyService.getVerifyCode(key));
            return result;
        } catch (BusinessRuntimeException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("系统异常");
        }
        return result;
    }
}
