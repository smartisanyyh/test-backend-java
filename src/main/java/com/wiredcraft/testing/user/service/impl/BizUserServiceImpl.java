package com.wiredcraft.testing.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiredcraft.testing.common.enums.Constants;
import com.wiredcraft.testing.common.utils.WebUtil;
import com.wiredcraft.testing.user.domain.po.BizUser;
import com.wiredcraft.testing.user.service.BizUserService;
import com.wiredcraft.testing.user.mapper.BizUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser>
    implements BizUserService{
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> nearfriend(Double distance) {
        BizUser loginUser = WebUtil.getLoginUser();
        assert loginUser != null;
        Circle circle = new Circle(new Point(loginUser.getLongitude(), loginUser.getLatitude()),
                new Distance(distance, Metrics.KILOMETERS));
        //todo page and get other info
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearFriends = redisTemplate.opsForGeo()
                .radius(Constants.USER_FRIEND_PREFIX +
                        Objects.requireNonNull(WebUtil.getLoginUser()).getId(), circle, args);
        return nearFriends;
    }
}




