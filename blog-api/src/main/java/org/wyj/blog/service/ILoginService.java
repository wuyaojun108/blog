package org.wyj.blog.service;

import org.wyj.blog.entity.dos.SysUser;
import org.wyj.blog.entity.param.LoginParam;
import org.wyj.blog.entity.param.RegisterParam;

public interface ILoginService {
    String login(LoginParam loginParams);

    String currentUser(String token);

    String logout(String token);

    String register(RegisterParam registerParam);

    SysUser getUserByToken(String token);
}
