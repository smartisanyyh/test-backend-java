package com.wiredcraft.testing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCES(0, "success"),
    ERROR(-1, "system error"),
    PARAM_ERROR(1, "param error"),
    REQUIRE_LOGIN(2, "please login first"),
    LOGIN_ERROR(3, "wrong username or password"),
    DUPLICATE_NAME(4, "duplicate name"),
    ;
    private int code;
    private String msg;
}
