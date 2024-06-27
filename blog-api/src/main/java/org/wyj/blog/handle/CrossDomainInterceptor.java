package org.wyj.blog.handle;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossDomainInterceptor implements HandlerInterceptor {

    // 处理OPTIONS请求
    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.addHeader("Vary", "Origin");
            response.addHeader("Vary", "Access-Control-Request-Method");
            response.addHeader("Vary", "Access-Control-Request-Headers");
            response.setHeader("Access-control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST");
            response.setHeader("Access-Control-Allow-Credentials", "false");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
            response.setHeader("Access-Control-Max-Age", "1800");
            response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
            return false;
        }
        // 为所有的响应加上一个统一的响应头
        response.setHeader("Access-control-Allow-Origin", "*");
        return true;
    }

}
