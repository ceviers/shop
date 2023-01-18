package com.cevier.shop;

import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;

import java.util.List;

public interface ItemService {
    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);
}
