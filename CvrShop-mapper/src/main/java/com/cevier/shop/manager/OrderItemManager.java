package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.OrderItems;

import java.util.List;

public interface OrderItemManager extends IService<OrderItems> {
    List<OrderItems> getByOrderId(String orderId);
}
