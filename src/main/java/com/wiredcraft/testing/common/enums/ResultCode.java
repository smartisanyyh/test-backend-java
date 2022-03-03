package com.wiredcraft.testing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCES(0, "success"),
    ERROR(-1, "system error"),
    PARAM_ERROR(1, "param error"),
    ;
    private int code;
    private String msg;
}
