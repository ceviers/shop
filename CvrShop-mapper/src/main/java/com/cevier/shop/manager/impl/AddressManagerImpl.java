package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.AddressManager;
import com.cevier.shop.mapper.UserAddressMapper;
import com.cevier.shop.pojo.UserAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressManagerImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements AddressManager {
    @Override
    public List<UserAddress> queryAll(String userId) {
        return this.lambdaQuery().eq(UserAddress::getUserId, userId).list();
    }

    @Override
    public List<UserAddress> getDefaultAddrByUserId(String userId) {
        return this.lambdaQuery().eq(UserAddress::getUserId, userId).eq(UserAddress::getIsDefault, 1).list();
    }
}
