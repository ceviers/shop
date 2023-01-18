package com.cevier.shop.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.ItemsComments;
import com.cevier.shop.pojo.vo.ItemCommentVO;

import java.util.Map;

public interface ItemsCommentsManager extends IService<ItemsComments> {
    Integer getCommentCounts(String itemId, Integer type);

    IPage<ItemCommentVO> queryItemComments(Map<String, Object> map, Integer page, Integer pageSize);
}
