package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.ItemsCommentsManager;
import com.cevier.shop.mapper.ItemsCommentsMapper;
import com.cevier.shop.pojo.ItemsComments;
import com.cevier.shop.pojo.vo.ItemCommentVO;
import com.cevier.shop.pojo.vo.MyCommentVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ItemsCommentsManagerImpl extends ServiceImpl<ItemsCommentsMapper, ItemsComments> implements ItemsCommentsManager {
    @Override
    public Integer getCommentCounts(String itemId, Integer commentLevel) {
        return this.lambdaQuery().eq(ItemsComments::getItemId, itemId)
                .eq(commentLevel != null, ItemsComments::getCommentLevel, commentLevel).count().intValue();
    }

    @Override
    public IPage<ItemCommentVO> queryItemComments(Map<String, Object> map, Integer page, Integer pageSize) {
        return this.baseMapper.queryItemComments(map, new Page<ItemsComments>(page, pageSize));
    }

    @Override
    public void saveComments(Map<String, Object> map) {
        this.baseMapper.saveComments(map);
    }

    @Override
    public IPage<MyCommentVO> queryMyComments(Map<String, Object> map, Integer page, Integer pageSize) {
        return this.baseMapper.queryMyComments(map, new Page<MyCommentVO>(page, pageSize));
    }
}
