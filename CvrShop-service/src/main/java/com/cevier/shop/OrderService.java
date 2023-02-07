package com.cevier.shop;

import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.bo.ShopcartBO;
import com.cevier.shop.pojo.bo.SubmitOrderBO;
import com.cevier.shop.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {
    OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO);

    void updateOrderStatus(String orderId, Integer orderStatus);

    OrderStatus queryOrderStatusInfo(String orderId);
}
