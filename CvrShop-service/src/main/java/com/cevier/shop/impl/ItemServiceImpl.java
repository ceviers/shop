package com.cevier.shop.impl;

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

    private Integer getCommentCounts(String itemId, Integer type) {
        return itemsCommentsManager.getCommentCounts(itemId, type);
    }
}
