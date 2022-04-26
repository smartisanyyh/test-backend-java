package com.wiredcraft.testing.user.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.testing.common.annotations.Anonymous;
import com.wiredcraft.testing.common.enums.Constants;
import com.wiredcraft.testing.common.enums.ResultCode;
import com.wiredcraft.testing.common.exception.ServiceException;
import com.wiredcraft.testing.common.utils.WebUtil;
import com.wiredcraft.testing.common.vo.PageVO;
import com.wiredcraft.testing.user.domain.po.BizUser;
import com.wiredcraft.testing.user.domain.ro.UserCreateRO;
import com.wiredcraft.testing.user.domain.ro.UserListRO;
import com.wiredcraft.testing.user.domain.ro.UserLoginRO;
import com.wiredcraft.testing.user.domain.ro.UserModifyRO;
import com.wiredcraft.testing.user.domain.vo.UserInfoVO;
import com.wiredcraft.testing.user.service.BizUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    BizUserService bizUserService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * get a user info by id2
     *2
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public UserInfoVO get(@PathVariable Long id) {
        BizUser user = bizUserService.getById(id);
        if (user == null) {
            return null;
        }
        UserInfoVO result = new UserInfoVO();
        //remove redundant fields
        BeanUtils.copyProperties(user, result);
        return result;
    }

    /**
     * list all users or page users
     *
     * @param userListRO
     * @return
     */
    @GetMapping("list")
    public PageVO<UserInfoVO> list(@RequestBody UserListRO userListRO) {
        Page<BizUser> page = WebUtil.getPage(userListRO);
        bizUserService.lambdaQuery()
                .like(StrUtil.isNotBlank(userListRO.getName()), BizUser::getName, userListRO.getName())
                .like(StrUtil.isNotBlank(userListRO.getAddress()), BizUser::getAddress, userListRO.getAddress())
                .like(StrUtil.isNotBlank(userListRO.getDescription()), BizUser::getDescription, userListRO.getDescription())
                .page(page);
        return WebUtil.transPage(page, UserInfoVO.class);

    }

    /**
     * create a user
     *
     * @param userCreateRO
     * @return
     */
    @Anonymous
    @PostMapping
    public Long create(@RequestBody @Valid UserCreateRO userCreateRO) {
        BizUser user = new BizUser();
        BeanUtils.copyProperties(userCreateRO, user);
        user.setPwd(DigestUtil.md5Hex(user.getPwd()));
        Optional<BizUser> bizUser = bizUserService.lambdaQuery().eq(BizUser::getName, userCreateRO.getName()).oneOpt();
        if (bizUser.isPresent()) {
            throw new ServiceException(ResultCode.DUPLICATE_NAME);
        }
        bizUserService.save(user);
        return user.getId();
    }

    /**
     * delete user by id
     *
     * @param id
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        bizUserService.removeById(id);
    }

    /**
     * modify user by id
     *
     * @param userModifyRO
     */
    @PutMapping
    public void put(@RequestBody @Valid UserModifyRO userModifyRO) {
        BizUser user = new BizUser();
        BeanUtils.copyProperties(userModifyRO, user);
        user.setPwd(DigestUtil.md5Hex(user.getPwd()));
        bizUserService.updateById(user);
    }


    /**
     * login
     *
     * @param userLoginRO
     * @return
     */
    @Anonymous
    @PostMapping("login")
    public String login(@RequestBody @Valid UserLoginRO userLoginRO) {
        userLoginRO.setPwd(DigestUtil.md5Hex(userLoginRO.getPwd()));
        Optional<BizUser> bizUser = bizUserService.lambdaQuery().eq(BizUser::getName, userLoginRO.getName())
                .eq(BizUser::getPwd, userLoginRO.getPwd()).oneOpt();
        if (bizUser.isPresent()) {
            String token = IdUtil.randomUUID();
            redisTemplate.opsForValue().set(Constants.USER_TOKEN_PREFIX + token, bizUser.get(), 30, TimeUnit.MINUTES);
            return token;
        } else {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
    }

    /**
     * add a friend
     *
     * @param id the other user id
     * @return
     */
    @PostMapping("friend/{id}")
    public void friend(@PathVariable Long id) {
        BizUser loginUser = WebUtil.getLoginUser();
        assert loginUser != null;
        if (loginUser.getId().equals(id)) {
            throw new ServiceException(ResultCode.ADD_SELF_ERROR);
        }
        //add current user's friend
        BizUser friend = bizUserService.getById(id);
        if (null == friend) {
            throw new ServiceException(ResultCode.USER_NOT_EXISTS);
        }
        redisTemplate.opsForGeo().add(Constants.USER_FRIEND_PREFIX + loginUser.getId(),
                new Point(friend.getLongitude(), friend.getLatitude()), id);
        //todo user confirmation & other biz check
        //add other user's friend
        redisTemplate.opsForGeo().add(Constants.USER_FRIEND_PREFIX + id,
                new Point(loginUser.getLongitude(), loginUser.getLatitude()), loginUser.getId());
    }

    /**
     * get current user's friends id
     *
     * @return
     */
    @GetMapping("friend")
    public Set<Object> getAllfriend() {
        //todo page and get other info
        return redisTemplate.opsForZSet().range(Constants.USER_FRIEND_PREFIX +
                Objects.requireNonNull(WebUtil.getLoginUser()).getId(), 0, -1);
    }


    /**
     * Get people near the current user
     *
     * @return
     */
    @GetMapping("friend/near/{distance}")
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> nearfriend(@PathVariable Double distance) {
        return bizUserService.nearfriend(distance);

    }


}
