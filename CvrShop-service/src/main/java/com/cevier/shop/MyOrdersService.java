package com.cevier.shop;

import com.cevier.shop.pojo.Orders;

public interface MyOrdersService {
    Orders queryMyOrder(String userId, String orderId);
}
