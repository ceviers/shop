package com.cevier.shop.center;

import com.cevier.shop.pojo.OrderItems;
import com.cevier.shop.pojo.bo.center.OrderItemsCommentBO;
import com.cevier.shop.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {
    List<OrderItems> queryPendingComment(String orderId);

    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
