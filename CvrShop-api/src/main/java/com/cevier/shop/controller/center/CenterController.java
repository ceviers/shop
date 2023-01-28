package com.cevier.shop.controller.center;

import com.cevier.shop.center.CenterUserService;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.utils.ApiJsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户中心展示的相关接口")
@RestController
@RequestMapping("/center")
public class CenterController {
    @Resource
    private CenterUserService centerUserService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public ApiJsonResult userInfo(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId) {

        Users user = centerUserService.queryUserInfo(userId);
        return ApiJsonResult.ok(user);
    }

}
