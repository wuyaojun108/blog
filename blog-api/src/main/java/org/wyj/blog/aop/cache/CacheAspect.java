package org.wyj.blog.aop.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class CacheAspect {
    private static final Logger LOG = LoggerFactory.getLogger(CacheAspect.class);

    private static final String DOUBLE_COLON = "::";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(org.wyj.blog.aop.cache.Cache)")
    public void pt() { }

    @Around("pt()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        String paramStr = Arrays.toString(args);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        long expire = cacheAnnotation.expire();
        String cacheName = cacheAnnotation.name();

        String redisKey = className + DOUBLE_COLON +
                methodName + DOUBLE_COLON + paramStr;
        // Object redisValue = redisTemplate.opsForHash().get(redisKey, redisKey.hashCode());
        Object redisValue = redisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
//            LOG.info("走了缓存~~~: {}.{}({})", className, methodName, paramStr);
            return redisValue;
        }
        // 执行连接点的方法
        Object result;
        try {
            if (args.length > 0) {
                result = joinPoint.proceed(args);
            } else {
                result = joinPoint.proceed();
            }
        } catch (Throwable e) {
            throw new Exception(e);
        }
        // redisTemplate.opsForHash().put(redisKey, redisKey.hashCode(), result);
        redisTemplate.opsForValue().set(redisKey, (String)result, Duration.ofSeconds(86400)); // 1天的过期时间
//        LOG.info("存入缓存~~~: {}.{}({})", className, methodName, paramStr);
        return result;
    }
}
