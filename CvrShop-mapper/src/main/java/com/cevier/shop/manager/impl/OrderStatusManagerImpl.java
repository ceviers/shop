package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.OrderStatusManager;
import com.cevier.shop.mapper.OrderStatusMapper;
import com.cevier.shop.pojo.OrderStatus;
import org.springframework.stereotype.Repository;

@Repository
public class OrderStatusManagerImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusManager {
}
