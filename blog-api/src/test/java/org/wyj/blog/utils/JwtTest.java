package org.wyj.blog.utils;

import java.util.Map;

public class JwtTest {
    public static void main(String[] args) {
        String token = JwtUtil.createToken(101L);
        // eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDQwMDg2MDAsInVzZXJJZCI6MTAwLCJpYXQiOjE3MDM5MjIyMDB9.nQtMBcK4MeDkFdbPa6eycXZaMwW5W8DTy7-yZ8aaaSM
        System.out.println("token = " + token);
        Map<String, Object> map = JwtUtil.checkToken(token);
        // 100
        System.out.println("map.get(\"userId\") = " + map.get("userId"));
    }
}
