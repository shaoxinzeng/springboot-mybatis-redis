package com.hkd.interceptor;


import com.hkd.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by 1 on 2017-08-16.
 * 登录拦截器
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String deviceId = request.getParameter("deviceId");
        String telephone = request.getParameter("mobilePhone");
        redisService.getLoginUser(deviceId, telephone);
        // System.out.println("登录拦截");
        return true;
    }
}

