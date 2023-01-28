package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.vo.MyOrdersVO;

import java.util.Map;

public interface OrdersManager extends IService<Orders> {
    Orders queryMyOrder(String userId, String orderId);

    int getMyOrderStatusCounts(Map<String, Object> map);

    Page<MyOrdersVO> queryMyOrders(Map<String, Object> map, Integer page, Integer pageSize);

    Page<OrderStatus> getMyOrderTrend(Map<String, Object> map, Integer page, Integer pageSize);
}
