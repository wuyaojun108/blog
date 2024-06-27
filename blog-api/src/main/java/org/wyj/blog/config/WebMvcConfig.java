package org.wyj.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.wyj.blog.handle.CrossDomainInterceptor;
import org.wyj.blog.handle.LoginInterceptor;

// web配置
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;


    @Autowired
    private LoginInterceptor loginInterceptor;

//    // 跨域配置
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // addMapping表示允许跨域配置处理的路径，/** 是ANT风格的通配符，表示所有路径
//        registry.addMapping("/**")
//                // 配置当前路径允许哪个域名访问。
//                .allowedOrigins("http://localhost:8080");
//    }

    // 发布文章和创建评论需要登录后才可以进行
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义跨域配置拦截器，使用默认的跨域配置，无法在拦截器中生效，因为拦截器的调用在跨域配置之前
        // 所以自定义一个拦截器处理跨域请求，它在所有的拦截器之前调用，避免在其它拦截器中添加跨域逻辑
        registry.addInterceptor(crossDomainInterceptor).addPathPatterns("/**");

        registry.addInterceptor(loginInterceptor)
                // 正确的配置方式
                // .addPathPatterns("/**").excludePathPatterns("/user/**")
                .addPathPatterns("/api/v1/comment/create")
                .addPathPatterns("/api/v1/article/publish");
    }
}
