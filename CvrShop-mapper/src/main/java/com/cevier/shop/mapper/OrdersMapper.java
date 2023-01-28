package com.cevier.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface OrdersMapper extends BaseMapper<Orders> {

    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    Page<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map, IPage<MyOrdersVO> myOrdersVOPage);

    Page<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map, Page<Object> objectPage);
}