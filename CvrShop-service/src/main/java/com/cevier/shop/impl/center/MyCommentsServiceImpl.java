package com.cevier.shop.impl.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cevier.shop.BaseService;
import com.cevier.shop.center.MyCommentsService;
import com.cevier.shop.manager.ItemsCommentsManager;
import com.cevier.shop.manager.OrderItemManager;
import com.cevier.shop.manager.OrderStatusManager;
import com.cevier.shop.manager.OrdersManager;
import com.cevier.shop.pojo.OrderItems;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.bo.center.OrderItemsCommentBO;
import com.cevier.shop.pojo.vo.MyCommentVO;
import com.cevier.shop.utils.PagedGridResult;
import jakarta.annotation.Resource;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {
    @Resource
    private OrderItemManager orderItemManager;

    @Resource
    private ItemsCommentsManager itemsCommentsManager;

    @Resource
    private Sid sid;

    @Resource
    private OrdersManager ordersManager;

    @Resource
    private OrderStatusManager orderStatusManager;

    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        return orderItemManager.getByOrderId(orderId);
    }

    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsManager.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(1);
        ordersManager.updateById(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusManager.updateById(orderStatus);
    }

    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        IPage<MyCommentVO> list = itemsCommentsManager.queryMyComments(map, page, pageSize);

        return setterPagedGrid(list);
    }
}
