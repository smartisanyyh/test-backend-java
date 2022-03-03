package com.wiredcraft.testing.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private Long total;
    private List<T> dataList;
}
