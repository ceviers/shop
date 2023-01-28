package com.cevier.shop.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cevier.shop.BaseService;
import com.cevier.shop.MyOrdersService;
import com.cevier.shop.enums.OrderStatusEnum;
import com.cevier.shop.manager.OrderStatusManager;
import com.cevier.shop.manager.OrdersManager;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.vo.MyOrdersVO;
import com.cevier.shop.pojo.vo.OrderStatusCountsVO;
import com.cevier.shop.utils.PagedGridResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {
    @Resource
    private OrdersManager ordersManager;

    @Resource
    private OrderStatusManager orderStatusManager;

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        return ordersManager.queryMyOrder(userId, orderId);
    }

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersManager.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersManager.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersManager.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", 0);
        int waitCommentCounts = ordersManager.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
        return countsVO;
    }

    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        Page<MyOrdersVO> list = ordersManager.queryMyOrders(map, page, pageSize);

        return setterPagedGrid(list);
    }

    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderId(orderId);
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        orderStatusManager.updateById(updateOrder);
    }

    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderId(orderId);
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        return orderStatusManager.updateById(updateOrder);
    }

    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setId(orderId);
        updateOrder.setIsDelete(1);
        updateOrder.setUpdatedTime(new Date());

        return ordersManager.updateById(updateOrder);
    }

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        Page<OrderStatus> list = ordersManager.getMyOrderTrend(map, page, pageSize);

        return setterPagedGrid(list);
    }
}
