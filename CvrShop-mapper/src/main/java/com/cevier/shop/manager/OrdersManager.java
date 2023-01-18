package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Orders;

public interface OrdersManager extends IService<Orders> {
    Orders queryMyOrder(String userId, String orderId);
}
