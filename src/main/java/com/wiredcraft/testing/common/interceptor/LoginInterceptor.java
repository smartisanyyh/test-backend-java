package com.wiredcraft.testing.common.interceptor;

import com.wiredcraft.testing.common.annotations.Anonymous;
import com.wiredcraft.testing.common.enums.ResultCode;
import com.wiredcraft.testing.common.exception.ServiceException;
import com.wiredcraft.testing.common.utils.WebUtil;
import com.wiredcraft.testing.user.domain.po.BizUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    //调用请求的时候执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            Anonymous methodAnnotation = h.getMethodAnnotation(Anonymous.class);
            if (null != methodAnnotation) {
                return true;
            }
        }
        BizUser loginUser = WebUtil.getLoginUser();
        if (loginUser == null) {
            throw new ServiceException(ResultCode.REQUIRE_LOGIN);
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebUtil.clear();
    }

}