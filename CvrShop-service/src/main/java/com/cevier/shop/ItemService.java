package com.cevier.shop;

import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;
import com.cevier.shop.pojo.vo.CommentLevelCountsVO;
import com.cevier.shop.utils.PagedGridResult;

import java.util.List;

public interface ItemService {
    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);

    CommentLevelCountsVO queryCommentCounts(String itemId);

    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);
}
