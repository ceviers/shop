package com.cevier.shop.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.vo.SearchItemsVO;
import com.cevier.shop.pojo.vo.ShopCartVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ItemManager extends IService<Items> {
    Items queryItemById(String itemId);

    IPage<SearchItemsVO> searchItems(Map<String, Object> map, Integer page, Integer pageSize);

    IPage<SearchItemsVO> searchItemsByThirdCat(Map<String, Object> map, Integer page, Integer pageSize);

    List<ShopCartVO> queryItemsBySpecIds(Collection<String> itemSpecIds);
}
