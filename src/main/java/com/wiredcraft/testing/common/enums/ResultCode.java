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
    USER_NOT_EXISTS(5, "user not exists"),
    ADD_SELF_ERROR(5, "do not add your self"),
    ;
    private final int code;
    private final String msg;
}
