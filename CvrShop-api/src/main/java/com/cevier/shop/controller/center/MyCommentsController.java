package com.cevier.shop.controller.center;

import com.cevier.shop.center.MyCommentsService;
import com.cevier.shop.controller.BaseController;
import com.cevier.shop.pojo.OrderItems;
import com.cevier.shop.pojo.Orders;
import com.cevier.shop.pojo.bo.center.OrderItemsCommentBO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.PagedGridResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "用户中心评价模块")
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    @Resource
    private MyCommentsService myCommentsService;


    @Operation(summary = "查询订单列表")
    @PostMapping("/pending")
    public ApiJsonResult pending(
            @RequestParam String userId,
            @RequestParam String orderId) {

        // 判断用户和订单是否关联
        ApiJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (myOrder.getIsComment() == 1) {
            return ApiJsonResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return ApiJsonResult.ok(list);
    }


    @Operation(summary = "保存评论列表")
    @PostMapping("/saveList")
    public ApiJsonResult saveList(
            @RequestParam String userId,
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        // 判断用户和订单是否关联
        ApiJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return ApiJsonResult.errorMsg("评论内容不能为空！");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return ApiJsonResult.ok();
    }

    @Operation(summary = "查询我的评价")
    @PostMapping("/query")
    public ApiJsonResult query(
            @RequestParam String userId,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myCommentsService.queryMyComments(userId,
                page,
                pageSize);

        return ApiJsonResult.ok(grid);
    }
}
