package org.wyj.blog.service;

import org.wyj.blog.entity.dos.SysUser;

public interface ISysUserService {
    SysUser findUserById(Long authorId);

    SysUser findUser(String account, String password);

    SysUser findUserByAccount(String account);

    int saveUser(SysUser sysUser);
}
