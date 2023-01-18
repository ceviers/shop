package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemManager;
import com.cevier.shop.mapper.ItemsMapper;
import com.cevier.shop.pojo.Items;
import org.springframework.stereotype.Repository;

@Repository
public class ItemManagerImpl extends ServiceImpl<ItemsMapper, Items> implements ItemManager {

    @Override
    public Items queryItemById(String itemId) {
        return this.baseMapper.selectById(itemId);
    }
}
