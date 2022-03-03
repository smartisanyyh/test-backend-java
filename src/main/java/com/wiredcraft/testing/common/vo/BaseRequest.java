package com.wiredcraft.testing.common.vo;

import lombok.Data;

@Data
public class BaseRequest {
    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;
    private Boolean asc = false;
}
