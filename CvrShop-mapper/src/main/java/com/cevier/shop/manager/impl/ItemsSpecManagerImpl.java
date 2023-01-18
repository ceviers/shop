package com.cevier.shop.manager.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemsSpecManager;
import com.cevier.shop.mapper.ItemsSpecMapper;
import com.cevier.shop.pojo.ItemsSpec;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemsSpecManagerImpl extends ServiceImpl<ItemsSpecMapper, ItemsSpec> implements ItemsSpecManager {
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        return this.lambdaQuery().eq(ItemsSpec::getItemId, itemId).list();
    }
}
