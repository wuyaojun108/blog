package org.wyj.blog.utils;

import org.wyj.blog.entity.dos.SysUser;

public class UserThreadLocalUtil {
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    private UserThreadLocalUtil() { }

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
