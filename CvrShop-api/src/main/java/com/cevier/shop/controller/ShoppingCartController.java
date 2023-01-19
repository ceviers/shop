package com.cevier.shop.controller;

import com.cevier.shop.pojo.bo.passport.ShopcartBO;
import com.cevier.shop.utils.ApiJsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopcart")
@Tag(name = "购物车接口相关的api")
public class ShoppingCartController {
    @Operation(summary = "添加商品到购物车")
    @PostMapping("/add")
    public ApiJsonResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }

        System.out.println(shopcartBO);

        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return ApiJsonResult.ok();
    }

    @Operation(summary = "从购物车中删除商品")
    @PostMapping("/del")
    public ApiJsonResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return ApiJsonResult.errorMsg("参数不能为空");
        }

        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return ApiJsonResult.ok();
    }
}
