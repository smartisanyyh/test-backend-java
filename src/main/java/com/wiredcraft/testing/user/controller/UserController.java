package com.wiredcraft.testing.user.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiredcraft.testing.common.util.WebUtil;
import com.wiredcraft.testing.common.vo.PageVO;
import com.wiredcraft.testing.user.domain.po.BizUser;
import com.wiredcraft.testing.user.domain.ro.UserCreateRO;
import com.wiredcraft.testing.user.domain.ro.UserListRO;
import com.wiredcraft.testing.user.domain.ro.UserModifyRO;
import com.wiredcraft.testing.user.domain.vo.UserInfoVO;
import com.wiredcraft.testing.user.service.BizUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    BizUserService bizUserService;

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

    @PostMapping
    public void create(@RequestBody @Valid UserCreateRO userCreateRO) {
        BizUser user = new BizUser();
        BeanUtils.copyProperties(userCreateRO, user);
        user.setPwd(DigestUtil.md5Hex(user.getPwd()));
        bizUserService.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        bizUserService.removeById(id);
    }


    @PutMapping
    public void put(@RequestBody @Valid UserModifyRO userModifyRO) {
        BizUser user = new BizUser();
        BeanUtils.copyProperties(userModifyRO, user);
        user.setPwd(DigestUtil.md5Hex(user.getPwd()));
        bizUserService.updateById(user);
    }
}
