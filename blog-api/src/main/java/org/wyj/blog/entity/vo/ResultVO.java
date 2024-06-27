package org.wyj.blog.entity.vo;

import org.wyj.blog.entity.enums.ErrorCode;
import org.wyj.blog.utils.JsonUtil;

public class ResultVO {
    private int code;
    private String msg;
    private Object data;

    public ResultVO() {
    }

    public ResultVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResultVO success(Object data) {
        return new ResultVO(200, "success", data);
    }

    public static ResultVO fail(String msg) {
        return new ResultVO(400, msg, null);
    }

    public static ResultVO fail(ErrorCode errorCode) {
        return new ResultVO(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}
