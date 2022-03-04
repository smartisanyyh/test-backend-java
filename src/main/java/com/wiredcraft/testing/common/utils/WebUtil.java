package com.wiredcraft.testing.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.testing.common.vo.BaseRequest;
import com.wiredcraft.testing.common.vo.PageVO;
import com.wiredcraft.testing.user.domain.po.BizUser;
import com.wiredcraft.testing.user.service.BizUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebUtil {
    private static RedisTemplate<String, Object> redisTemplate;

    private static NamedThreadLocal<BizUser> bizUserNamedThreadLocal = new NamedThreadLocal<>("login_user");
    private static NamedThreadLocal<Boolean> hasLoginUser = new NamedThreadLocal<>("hasLoginUser");

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            return null;
        }
        HttpServletRequest request = ra.getRequest();
        return request;
    }

    public static final String USER_TOKEN_PREFIX = "wiredcraft:testing:user:logintoken:";

    public static BizUser getLoginUser() {
        if (hasLoginUser.get() != null && hasLoginUser.get()) {
            return bizUserNamedThreadLocal.get();
        }
        if (hasLoginUser.get() != null && !hasLoginUser.get()) {
            //already query  but null
            return null;
        }
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            hasLoginUser.set(false);
            return null;
        }
        String key = USER_TOKEN_PREFIX + token;
        BizUser bizUser = (BizUser) redisTemplate.opsForValue().get(key);
        if (null != bizUser) {
            //extend expiration time
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            bizUserNamedThreadLocal.set(bizUser);
            hasLoginUser.set(true);
            return bizUser;
        } else {
            hasLoginUser.set(false);
            return null;
        }
    }

    public static void clear() {
        hasLoginUser.remove();
        bizUserNamedThreadLocal.remove();
    }


    @Autowired
    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate) {
        WebUtil.redisTemplate = redisTemplate;
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
        result.setDataList(transListVO(page.getRecords(), tClass));
        return result;
    }


    public static <T> T transVO(Object object, Class<T> tClass) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(object), tClass);
    }


}
