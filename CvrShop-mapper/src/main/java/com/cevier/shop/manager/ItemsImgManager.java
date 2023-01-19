package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.ItemsImg;

import java.util.List;

public interface ItemsImgManager extends IService<ItemsImg> {
    List<ItemsImg> queryItemImgList(String itemId);

    String queryItemMainImgById(String itemId);
}
