package org.wyj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wyj.blog.entity.dos.SysUser;

//@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser findUserById(Long authorId);
}
