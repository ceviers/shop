package com.cevier.shop.impl;

import com.cevier.shop.ItemService;
import com.cevier.shop.manager.ItemManager;
import com.cevier.shop.manager.ItemsImgManager;
import com.cevier.shop.manager.ItemsSpecManager;
import com.cevier.shop.manager.ItemsParamManager;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemManager itemManager;

    @Resource
    private ItemsImgManager itemsImgManager;

    @Resource
    private ItemsSpecManager itemsSpecManager;

    @Resource
    private ItemsParamManager itemsParamManager;

    @Override
    public Items queryItemById(String itemId) {
        return itemManager.queryItemById(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        return itemsImgManager.queryItemImgList(itemId);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        return itemsSpecManager.queryItemSpecList(itemId);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        return itemsParamManager.queryItemParam(itemId);
    }
}
