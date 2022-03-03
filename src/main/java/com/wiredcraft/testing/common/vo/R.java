package com.wiredcraft.testing.common.vo;

import com.wiredcraft.testing.common.enums.ResultCode;
import lombok.Data;

@Data
public class R {
    public int code;
    public String msg;
    public Object data;

    private R(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    private R(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    private R() {
    }

    public static R ok(Object data) {
        return new R(ResultCode.SUCCES, data);
    }

    public static R error(ResultCode resultCode) {
        return new R(resultCode);
    }

    public static R error(ResultCode resultCode,Object data) {
        return new R(resultCode,data);
    }
}
