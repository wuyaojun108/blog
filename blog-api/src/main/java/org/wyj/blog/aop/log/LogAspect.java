package org.wyj.blog.aop.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wyj.blog.utils.DateUtil;
import org.wyj.blog.utils.HttpContextUtil;
import org.wyj.blog.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Order(1)
public class LogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(org.wyj.blog.aop.log.LogAnnotation)")
    public void pt() { }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long executeTime = end - start;

        recordLog(start, executeTime, joinPoint);
        return result;
    }

    private void recordLog(long start, long executeTime, ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        String methodName = method.getName();
        Class<?> aClass = method.getDeclaringClass();
        String methodFullName = aClass.getName() + "." + methodName;
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();

        LOG.info("module:{}, operation:{}, startTime: {}, executedTime:{}ms, method:{}, param: {}, ip:{}",
                logAnnotation.module(), logAnnotation.operation(),
                DateUtil.convertTimestampToStr(start, "yyyy-MM-dd HH:mm:ss"),
                executeTime, methodFullName,
                Arrays.toString(args), IpUtil.getIpAddress(request));
    }
}
