package org.wyj.blog.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.wyj.blog.entity.dos.SysUser;
import org.wyj.blog.entity.enums.ErrorCode;
import org.wyj.blog.entity.vo.ResultVO;
import org.wyj.blog.service.ILoginService;
import org.wyj.blog.utils.UserThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private ILoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 判断当前请求是不是访问控制器的方法，如果是访问静态资源，那么handler是
        // RequestResourceHandler，并且它会去classpath下的static目录中寻找
        // 静态资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        SysUser sysUser = loginService.getUserByToken(token);
        if (sysUser == null) {
            String resultJson = ResultVO.fail(ErrorCode.NO_LOGIN).toJson();
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(resultJson);
            return false;
        }
        LOG.info("request uri {}, method {}", request.getRequestURI(), request.getMethod());
        UserThreadLocalUtil.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        UserThreadLocalUtil.remove();
    }
}
