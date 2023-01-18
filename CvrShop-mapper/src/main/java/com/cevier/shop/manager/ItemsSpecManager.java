package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.ItemsSpec;

import java.util.List;

public interface ItemsSpecManager extends IService<ItemsSpec> {
    List<ItemsSpec> queryItemSpecList(String itemId);
}
