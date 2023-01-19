package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemManager;
import com.cevier.shop.mapper.ItemsMapper;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.vo.SearchItemsVO;
import com.cevier.shop.pojo.vo.ShopCartVO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class ItemManagerImpl extends ServiceImpl<ItemsMapper, Items> implements ItemManager {

    @Override
    public Items queryItemById(String itemId) {
        return this.baseMapper.selectById(itemId);
    }

    @Override
    public IPage<SearchItemsVO> searchItems(Map<String, Object> map, Integer page, Integer pageSize) {
        return this.baseMapper.searchItems(map, new Page<SearchItemsVO>(page, pageSize));
    }

    @Override
    public IPage<SearchItemsVO> searchItemsByThirdCat(Map<String, Object> map, Integer page, Integer pageSize) {
        return this.baseMapper.searchItemsByThirdCat(map, new Page<SearchItemsVO>(page, pageSize));
    }

    @Override
    public List<ShopCartVO> queryItemsBySpecIds(Collection<String> itemSpecIds) {
        return this.baseMapper.queryItemsBySpecIds(itemSpecIds);
    }

    @Override
    public int decreaseItemSpecStock(String itemSpecId, int buyCounts) {
        return this.baseMapper.decreaseItemSpecStock(itemSpecId, buyCounts);
    }
}
