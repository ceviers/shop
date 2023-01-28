package com.cevier.shop;

import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.vo.OrderStatusCountsVO;
import com.cevier.shop.utils.PagedGridResult;

public interface MyOrdersService {
    Orders queryMyOrder(String userId, String orderId);

    OrderStatusCountsVO getOrderStatusCounts(String userId);

    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    void updateDeliverOrderStatus(String orderId);

    boolean updateReceiveOrderStatus(String orderId);

    boolean deleteOrder(String userId, String orderId);

    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
