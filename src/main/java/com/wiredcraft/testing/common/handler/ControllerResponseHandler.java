package com.wiredcraft.testing.common.handler;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.testing.common.vo.PageVO;
import com.wiredcraft.testing.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * wrap response body
 */
@ControllerAdvice
@Slf4j
public class ControllerResponseHandler implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 支持所有的返回值类型
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof R) {
            return body;
        } else if (body instanceof Page) {
            Page page = (Page) body;
            PageVO pageVO = new PageVO();
            pageVO.setTotal(page.getTotal());
            pageVO.setDataList(page.getRecords());
            return R.ok(pageVO);
        } else {
            // 所有没有返回　RestResult　结构的结果均认为是成功的
            return R.ok(body);
        }
    }
}
