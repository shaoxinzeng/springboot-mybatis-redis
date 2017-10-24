package com.hkd.config;

import com.hkd.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 1 on 2017-08-16.
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private RequestInterceptor requestInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/loan/**")
                .addPathPatterns("/loan/get-confirm-loan")
        ;
        super.addInterceptors(registry);
    }
}
