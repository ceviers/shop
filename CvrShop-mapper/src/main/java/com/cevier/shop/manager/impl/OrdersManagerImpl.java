package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.OrdersManager;
import com.cevier.shop.mapper.OrdersMapper;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.vo.MyOrdersVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

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

        @Override
        public int getMyOrderStatusCounts(Map<String, Object> map) {
                return this.baseMapper.getMyOrderStatusCounts(map);
        }

        @Override
        public Page<MyOrdersVO> queryMyOrders(Map<String, Object> map, Integer page, Integer pageSize) {
                return this.baseMapper.queryMyOrders(map, new Page<MyOrdersVO>(page, pageSize));
        }

        @Override
        public Page<OrderStatus> getMyOrderTrend(Map<String, Object> map, Integer page, Integer pageSize) {
                return this.baseMapper.getMyOrderTrend(map, new Page<>(page, pageSize));
        }
}
