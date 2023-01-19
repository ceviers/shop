package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.OrderItemManager;
import com.cevier.shop.mapper.OrderItemsMapper;
import com.cevier.shop.pojo.OrderItems;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemManagerImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderItemManager {
}
