package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.OrdersManager;
import com.cevier.shop.mapper.OrdersMapper;
import com.cevier.shop.pojo.Orders;
import org.springframework.stereotype.Repository;

@Repository
public class OrdersManagerImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersManager {
        @Override
        public Orders queryMyOrder(String userId, String orderId) {
                return this.lambdaQuery().eq(Orders::getUserId, userId)
                        .eq(Orders::getId, orderId)
                        .eq(Orders::getIsDelete, 0)
                        .last("LIMIT 1")
                        .one();
        }
}
