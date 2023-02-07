package com.cevier.shop.controller;

import com.cevier.shop.OrderService;
import com.cevier.shop.enums.OrderStatusEnum;
import com.cevier.shop.enums.PayMethod;
import com.cevier.shop.pojo.OrderStatus;
import com.cevier.shop.pojo.bo.ShopcartBO;
import com.cevier.shop.pojo.bo.SubmitOrderBO;
import com.cevier.shop.pojo.vo.MerchantOrdersVO;
import com.cevier.shop.pojo.vo.OrderVO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.CookieUtils;
import com.cevier.shop.utils.JsonUtils;
import com.cevier.shop.utils.RedisOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Tag(name = "订单相关的api接口")
@RequestMapping("/orders")
@RestController
@Slf4j
public class OrdersController extends BaseController{
    @Resource
    private OrderService orderService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RedisOperator redisOperator;

    @Operation(summary = "用户下单")
    @PostMapping("/create")
    public ApiJsonResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type ) {
            return ApiJsonResult.errorMsg("支付方式不支持！");
        }

//        System.out.println(submitOrderBO.toString());
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopcartJson)) {
            return ApiJsonResult.errorMsg("购物数据不正确");
        }

        List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(shopcartList, submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        /**
         * 1001
         * 2002 -> 用户购买
         * 3003 -> 用户购买
         * 4004
         */
        // 完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        shopcartList.removeAll(orderVO.getToBeRemovedShopcatdList());
        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopcartList));
        // 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartList), true);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","imooc");

        HttpEntity<MerchantOrdersVO> entity =
                new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<ApiJsonResult> responseEntity =
                restTemplate.postForEntity(paymentUrl,
                        entity,
                        ApiJsonResult.class);
        ApiJsonResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            log.error("发送错误：{}", paymentResult.getMsg());
            return ApiJsonResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

        return ApiJsonResult.ok(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public ApiJsonResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return ApiJsonResult.ok(orderStatus);
    }
}
