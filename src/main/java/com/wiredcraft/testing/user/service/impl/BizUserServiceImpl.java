package com.wiredcraft.testing.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiredcraft.testing.user.domain.po.BizUser;
import com.wiredcraft.testing.user.service.BizUserService;
import com.wiredcraft.testing.user.mapper.BizUserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BizUserServiceImpl extends ServiceImpl<BizUserMapper, BizUser>
    implements BizUserService{

}




