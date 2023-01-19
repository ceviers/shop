package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.UserAddress;

import java.util.List;

public interface AddressManager extends IService<UserAddress> {
    List<UserAddress> queryAll(String userId);

    List<UserAddress> getDefaultAddrByUserId(String userId);
}
