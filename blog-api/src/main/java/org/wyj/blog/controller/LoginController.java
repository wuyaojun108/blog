package org.wyj.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wyj.blog.entity.param.LoginParam;
import org.wyj.blog.entity.param.RegisterParam;
import org.wyj.blog.service.ILoginService;
import org.wyj.blog.utils.JsonUtil;

/**
 * @author 武耀君
 * @date 2024/3/16
 *
 * 用户
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    // 登录。用户输入账号和密码，后端将用户输入的密码使用md5算法加上特定的盐，转换为一个md5字符串，
    // 然后根据账号和密码，从数据库中查找用户，如果有用户，根据用户ID和当前时间戳生成一个token，把
    // token作为键，用户信息作为值，存入到redis中，然后返回token
    @PostMapping("/login")
    public String login(@RequestBody String requestBody) {
        LoginParam loginParams = JsonUtil.fromJson(requestBody, LoginParam.class);
        return loginService.login(loginParams);
    }

    // 当前用户。用户把token放在请求头中，根据token，从redis中获取用户信息，然后返回给前端。
    @GetMapping("/currentUser")
    public String currentUser(@RequestHeader("Authorization") String token) {
        return loginService.currentUser(token);
    }

    // 登出。用户把token放到请求头中，然后后端根据token，找到redis中的数据后删除它。
    @GetMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

    // 注册，用户上传自己的信息，后端判断用户填写的账户是不存在的，如果存在，报错，如果没有报错，
    // 就把用户信息保存到ms_sys_user表中，然后生成token，把token和用户信息保存到redis中，
    // 返回token，此时，用户只要持有token，就算是登录状态。
    @PostMapping("/register")
    public String register(@RequestBody String requestBody) {
        RegisterParam registerParam = JsonUtil.fromJson(requestBody, RegisterParam.class);
        return loginService.register(registerParam);
    }

}
