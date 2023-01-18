package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.ItemsParam;

public interface ItemsParamManager extends IService<ItemsParam> {
    ItemsParam queryItemParam(String itemId);
}
