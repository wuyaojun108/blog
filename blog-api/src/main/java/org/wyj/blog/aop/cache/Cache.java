package org.wyj.blog.aop.cache;

import java.lang.annotation.*;

/**
 * 用于标识哪些方法的返回值需要缓存到redis
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    long expire() default 10 * 60;  // 缓存时间是10分钟
    String name();
}
