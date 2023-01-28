package com.cevier.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cevier.shop.pojo.ItemsComments;
import com.cevier.shop.pojo.vo.ItemCommentVO;
import com.cevier.shop.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    IPage<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map, IPage<ItemsComments> itemsCommentsPage);

    void saveComments(Map<String, Object> map);

    IPage<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map, IPage<MyCommentVO> myCommentVOPage);
}