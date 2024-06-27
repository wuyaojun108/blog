package org.wyj.blog.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json的工具类
 *
 * @auther 武耀君
 * @date   2023/12/25
 */
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder().create();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static <T> List<T> fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
