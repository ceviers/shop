package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Items;

public interface ItemManager extends IService<Items> {
    Items queryItemById(String itemId);
}
