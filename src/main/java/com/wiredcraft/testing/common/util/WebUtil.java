package com.wiredcraft.testing.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.testing.common.vo.BaseRequest;
import com.wiredcraft.testing.common.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class WebUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            return null;
        }
        HttpServletRequest request = ra.getRequest();
        return request;
    }


    public static <T> Page<T> getPage(BaseRequest baseRequest) {
        LinkedList<OrderItem> orderItems = new LinkedList<>();
        if (StrUtil.isNotBlank(baseRequest.getOrderBy())) {
            orderItems.add(new OrderItem(baseRequest.getOrderBy(), baseRequest.getAsc()));
        }
        if (baseRequest.getPageSize() != null && baseRequest.getPageNum() != null) {
            Page<T> tPage = new Page<>(baseRequest.getPageNum(), baseRequest.getPageSize());
            tPage.setOrders(orderItems);
            return tPage;
        } else {
            //get all
            Page<T> tPage = new Page<>(0, -1);
            tPage.setOrders(orderItems);
            return tPage;
        }
    }

    public static <T> List<T> transListVO(List list, Class<T> tClass) {
        return JSONUtil.toList(JSONUtil.toJsonStr(list), tClass);
    }

    public static <T> PageVO<T> transPage(Page page, Class<T> tClass) {
        PageVO<T> result = new PageVO<>();
        result.setTotal(page.getTotal());
        result.setDataList(transListVO(page.getRecords(),tClass));
        return result;
    }


    public static <T> T transVO(Object object, Class<T> tClass) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(object), tClass);
    }


}
