package com.cevier.shop.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cevier.shop.ItemService;
import com.cevier.shop.enums.CommentLevel;
import com.cevier.shop.manager.ItemManager;
import com.cevier.shop.manager.ItemsImgManager;
import com.cevier.shop.manager.ItemsSpecManager;
import com.cevier.shop.manager.ItemsParamManager;
import com.cevier.shop.manager.ItemsCommentsManager;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;
import com.cevier.shop.pojo.vo.CommentLevelCountsVO;
import com.cevier.shop.pojo.vo.ItemCommentVO;
import com.cevier.shop.pojo.vo.SearchItemsVO;
import com.cevier.shop.pojo.vo.ShopCartVO;
import com.cevier.shop.utils.DesensitizationUtil;
import com.cevier.shop.utils.PagedGridResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ItemsCommentsManager itemsCommentsManager;

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

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);

        return countsVO;
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        IPage<ItemCommentVO> list = itemsCommentsManager.queryItemComments(map, page, pageSize);
        for (ItemCommentVO vo : list.getRecords()) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        return setterPagedGrid(list);
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        IPage<SearchItemsVO> list = itemManager.searchItems(map, page, pageSize);

        return setterPagedGrid(list);
    }

    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);


        IPage<SearchItemsVO> list = itemManager.searchItemsByThirdCat(map, page, pageSize);

        return setterPagedGrid(list);
    }

    @Override
    public List<ShopCartVO> queryItemsBySpecIds(Collection<String> itemSpecIds) {
        return itemManager.queryItemsBySpecIds(itemSpecIds);
    }

    @Override
    public void decreaseItemSpecStock(String itemSpecId, int buyCounts) {
        int result = itemManager.decreaseItemSpecStock(itemSpecId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }
    }

    private PagedGridResult setterPagedGrid(IPage<?> list) {
        PagedGridResult grid = new PagedGridResult();
        grid.setPage((int)list.getCurrent());
        grid.setRows(list.getRecords());
        grid.setTotal((int)(Math.ceil(list.getTotal() / list.getSize())));
        grid.setRecords((int)list.getTotal());
        return grid;
    }

    private Integer getCommentCounts(String itemId, Integer type) {
        return itemsCommentsManager.getCommentCounts(itemId, type);
    }
}
