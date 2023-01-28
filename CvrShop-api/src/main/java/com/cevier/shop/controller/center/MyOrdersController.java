package com.cevier.shop.controller.center;

import com.cevier.shop.controller.BaseController;
import com.cevier.shop.pojo.vo.OrderStatusCountsVO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.PagedGridResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "用户中心我的订单")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Operation(summary= "获得订单状态数概况")
    @PostMapping("/statusCounts")
    public ApiJsonResult statusCounts(
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg(null);
        }

        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return ApiJsonResult.ok(result);
    }

    @Operation(summary= "查询订单列表")
    @PostMapping("/query")
    public ApiJsonResult query(
            @RequestParam String userId,
            @RequestParam(required = false) Integer orderStatus,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                orderStatus,
                page,
                pageSize);

        return ApiJsonResult.ok(grid);
    }


    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @Operation(summary= "商家发货")
    @GetMapping("/deliver")
    public ApiJsonResult deliver(
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return ApiJsonResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return ApiJsonResult.ok();
    }


    @Operation(summary= "用户确认收货")
    @PostMapping("/confirmReceive")
    public ApiJsonResult confirmReceive(
            @RequestParam String orderId,
            @RequestParam String userId) throws Exception {

        ApiJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return ApiJsonResult.errorMsg("订单确认收货失败！");
        }

        return ApiJsonResult.ok();
    }

    @Operation(summary= "用户删除订单")
    @PostMapping("/delete")
    public ApiJsonResult delete(
            @RequestParam String orderId,
            @RequestParam String userId) throws Exception {

        ApiJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return ApiJsonResult.errorMsg("订单删除失败！");
        }

        return ApiJsonResult.ok();
    }



    @Operation(summary= "查询订单动向")
    @PostMapping("/trend")
    public ApiJsonResult trend(
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

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                page,
                pageSize);

        return ApiJsonResult.ok(grid);
    }
}
