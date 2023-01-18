package com.cevier.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.vo.SearchItemsVO;
import com.cevier.shop.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ItemsMapper extends BaseMapper<Items> {

    IPage<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map, Page<SearchItemsVO> searchItemsVOPage);

    IPage<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map, Page<SearchItemsVO> searchItemsVOPage);

    List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") Collection<String> paramsList);
}