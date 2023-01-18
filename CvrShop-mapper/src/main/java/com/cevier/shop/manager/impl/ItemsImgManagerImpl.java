package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemsImgManager;
import com.cevier.shop.mapper.ItemsImgMapper;
import com.cevier.shop.pojo.ItemsImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemsImgManagerImpl extends ServiceImpl<ItemsImgMapper, ItemsImg> implements ItemsImgManager {
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        return this.lambdaQuery().eq(ItemsImg::getItemId, itemId).list();
    }
}
