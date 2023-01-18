package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemsParamManager;
import com.cevier.shop.mapper.ItemsParamMapper;
import com.cevier.shop.pojo.ItemsParam;
import org.springframework.stereotype.Repository;

@Repository
public class ItemsParamManagerImpl extends ServiceImpl<ItemsParamMapper, ItemsParam> implements ItemsParamManager {
    @Override
    public ItemsParam queryItemParam(String itemId) {
        return this.lambdaQuery().eq(ItemsParam::getItemId, itemId).last("LIMIT 1").one();
    }
}
