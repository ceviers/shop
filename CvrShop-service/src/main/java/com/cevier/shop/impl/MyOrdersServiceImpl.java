package com.cevier.shop.impl;

import com.cevier.shop.MyOrdersService;
import com.cevier.shop.manager.OrdersManager;
import com.cevier.shop.pojo.Orders;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MyOrdersServiceImpl implements MyOrdersService {
    @Resource
    private OrdersManager ordersManager;

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        return ordersManager.queryMyOrder(userId, orderId);
    }
}
