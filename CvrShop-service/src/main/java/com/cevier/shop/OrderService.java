package com.cevier.shop;

import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.bo.SubmitOrderBO;
import com.cevier.shop.pojo.vo.OrderVO;

public interface OrderService {
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    void updateOrderStatus(String orderId, Integer orderStatus);

    OrderStatus queryOrderStatusInfo(String orderId);
}
