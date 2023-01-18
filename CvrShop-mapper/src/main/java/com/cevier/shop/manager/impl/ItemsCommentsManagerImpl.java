package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemsCommentsManager;
import com.cevier.shop.mapper.ItemsCommentsMapper;
import com.cevier.shop.pojo.ItemsComments;
import org.springframework.stereotype.Repository;

@Repository
public class ItemsCommentsManagerImpl extends ServiceImpl<ItemsCommentsMapper, ItemsComments> implements ItemsCommentsManager {
    @Override
    public Integer getCommentCounts(String itemId, Integer commentLevel) {
        return this.lambdaQuery().eq(ItemsComments::getItemId, itemId)
                .eq(commentLevel != null, ItemsComments::getCommentLevel, commentLevel).count().intValue();
    }
}
