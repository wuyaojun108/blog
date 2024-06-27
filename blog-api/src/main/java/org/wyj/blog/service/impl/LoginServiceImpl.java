package org.wyj.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.wyj.blog.entity.dos.SysUser;
import org.wyj.blog.entity.enums.ErrorCode;
import org.wyj.blog.entity.vo.LoginUserVO;
import org.wyj.blog.entity.vo.ResultVO;
import org.wyj.blog.entity.param.LoginParam;
import org.wyj.blog.entity.param.RegisterParam;
import org.wyj.blog.service.ILoginService;
import org.wyj.blog.service.ISysUserService;
import org.wyj.blog.utils.JsonUtil;
import org.wyj.blog.utils.JwtUtil;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements ILoginService {
    private static final String SALT = "3edc#EDC34%^";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginParam loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return ResultVO.fail(ErrorCode.PARAM_ERROR).toJson();
        }

        password = DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return ResultVO.fail(ErrorCode.ACCOUNT_NOT_EXISTS).toJson();
        }

        String token = JwtUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue()
                .set("TOKEN_" + token, JsonUtil.toJson(sysUser), 1, TimeUnit.DAYS);
        return ResultVO.success(token).toJson();
    }

    @Override
    public String currentUser(String token) {
        if (StringUtils.isBlank(token)) {
            return ResultVO.fail(ErrorCode.NO_LOGIN).toJson();
        }
        Map<String, Object> map = JwtUtil.checkToken(token);
        if (map == null) {
            return ResultVO.fail(ErrorCode.TOKEN_ERROR).toJson();
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return ResultVO.fail(ErrorCode.TOKEN_ERROR).toJson();
        }

        SysUser sysUser = JsonUtil.fromJson(userJson, SysUser.class);
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setId(sysUser.getId());
        loginUserVO.setAccount(sysUser.getAccount());
        loginUserVO.setNickname(sysUser.getNickname());
        loginUserVO.setAvatar(sysUser.getAvatar());
        return ResultVO.success(loginUserVO).toJson();
    }

    @Override
    public String logout(String token) {
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return ResultVO.fail(ErrorCode.TOKEN_ERROR).toJson();
        }
        Boolean delete_flag = redisTemplate.delete("TOKEN_" + token);
        if (Boolean.TRUE.equals(delete_flag)) {
            return ResultVO.success(null).toJson();
        } else {
            return ResultVO.fail("退出失败").toJson();
        }
    }

    @Override
    @Transactional
    public String register(RegisterParam registerParam) {
        String account = registerParam.getAccount();
        String nickname = registerParam.getNickname();
        String password = registerParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(nickname)
                || StringUtils.isBlank(password)) {
            return ResultVO.fail(ErrorCode.PARAM_ERROR).toJson();
        }

        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return ResultVO.fail(ErrorCode.ACCOUNT_EXISTS).toJson();
        }

        password = DigestUtils.md5DigestAsHex((password + SALT).getBytes(StandardCharsets.UTF_8));
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setAdmin((byte) 0);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setDeleted((byte) 0);
        sysUser.setNickname(nickname);
        sysUser.setPassword(password);
        sysUser.setSalt(SALT);
        sysUser.setStatus("0");
        sysUserService.saveUser(sysUser);

        String token = JwtUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JsonUtil.toJson(sysUser),
                1, TimeUnit.DAYS);

        return ResultVO.success(token).toJson();
    }

    @Override
    public SysUser getUserByToken(String token){
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JwtUtil.checkToken(token);
        if (map == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }

        return JsonUtil.fromJson(userJson, SysUser.class);
    }
}
