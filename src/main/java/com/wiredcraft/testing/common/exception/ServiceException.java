package com.wiredcraft.testing.common.exception;

import com.wiredcraft.testing.common.enums.ResultCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;

    }
}
