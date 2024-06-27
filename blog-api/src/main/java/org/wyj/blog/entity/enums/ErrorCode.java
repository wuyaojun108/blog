package org.wyj.blog.entity.enums;

public enum ErrorCode {
    PARAM_ERROR(10001, "参数有误"),
    ACCOUNT_NOT_EXISTS(10002, "用户名或密码不存在"),
    NO_PERMISSION(10003, "没有权限"),
    SESSION_TIMEOUT(10004, "会话超时"),
    NO_LOGIN(10005, "没有登录"),
    TOKEN_ERROR(10006, "token错误"),
    ACCOUNT_EXISTS(10007, "账号已存在");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
