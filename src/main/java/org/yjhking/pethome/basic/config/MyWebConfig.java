package org.yjhking.pethome.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yjhking.pethome.basic.interceptor.LoginInterceptor;

/**
 * 通用配置类
 *
 * @author YJH
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    
    /**
     * 注册拦截器
     *
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(loginInterceptor)
                // 拦截所有
                .addPathPatterns("/**")
                // 放行登录
                .excludePathPatterns("/login/**")
                // 放行注册
                .excludePathPatterns("/user/*/register", "/verifyCode/**")
                .excludePathPatterns("/shop/settlement", "/fastDfs")
                .excludePathPatterns("/shop/active/*", "/user/register/*", "/user/active/*")
                .excludePathPatterns("/shop/export", "/shop/importExcel");
    }
}
