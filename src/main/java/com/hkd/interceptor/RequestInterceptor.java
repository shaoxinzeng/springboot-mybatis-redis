package com.hkd.interceptor;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;

/**
 * Created by 1 on 2017-08-16.
 * 登录拦截器
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*        System.out.println(request.getRequestURI());
        System.out.println(request.getCookies());
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());*/
        System.out.println("登录拦截");
        return true;
    }
}

