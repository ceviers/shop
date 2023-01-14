package com.cevier.shop.controller;

import com.cevier.shop.UserService;
import com.cevier.shop.pojo.bo.passport.UserBO;
import com.cevier.shop.utils.ApiJsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "注册与登入")
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Resource
    private UserService userService;

    @Operation(description = "判断用户名是否可用")
    @GetMapping("/usernameIsExist")
    public ApiJsonResult checkIfUserNameRepeated(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return ApiJsonResult.errorMsg("用户名不能为空");
        }
        if (userService.checkIfUserNameExist(username)) {
            return ApiJsonResult.errorMsg("用户名已存在");
        }
        return ApiJsonResult.ok();
    }

    @Operation(description = "注册账号")
    @PostMapping("/register")
    public ApiJsonResult register(@RequestBody UserBO userBO) {
        if (StringUtils.isBlank(userBO.getUsername())) {
            return ApiJsonResult.errorMsg("用户名不能为空");
        }
        if (StringUtils.isBlank(userBO.getPassword())) {
            return ApiJsonResult.errorMsg("密码不能为空");
        }
        if (userBO.getPassword().length() < 6) {
            return ApiJsonResult.errorMsg("密码至少包含六个字符");
        }
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return ApiJsonResult.errorMsg("两次输入的密码不一致");
        }
        if (userService.checkIfUserNameExist(userBO.getUsername())) {
            return ApiJsonResult.errorMsg("用户名已存在");
        }
        return ApiJsonResult.ok(userService.creatUser(userBO));
    }
}
