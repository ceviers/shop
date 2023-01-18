package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.ItemsComments;

public interface ItemsCommentsManager extends IService<ItemsComments> {
    Integer getCommentCounts(String itemId, Integer type);
}
