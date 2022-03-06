package com.wiredcraft.testing.user.service;

import com.wiredcraft.testing.user.domain.po.BizUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;

/**
 *
 */
public interface BizUserService extends IService<BizUser> {

    GeoResults<RedisGeoCommands.GeoLocation<Object>> nearfriend(Double distance);
}
