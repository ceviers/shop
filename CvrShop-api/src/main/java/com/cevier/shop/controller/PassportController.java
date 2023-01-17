package com.cevier.shop.controller;

import com.cevier.shop.UserService;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.passport.UserBO;
import com.cevier.shop.pojo.bo.passport.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.CookieUtils;
import com.cevier.shop.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "注册与登入")
@Slf4j
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Resource
    private UserService userService;

    @Operation(summary = "判断用户名是否可用")
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

    @Operation(summary = "注册账号")
    @PostMapping("/regist")
    public ApiJsonResult register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
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
        UserVO user = userService.creatUser(userBO);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
        return ApiJsonResult.ok(user);
    }

    @Operation(summary = "用户登入")
    @PostMapping("/login")
    public ApiJsonResult login(@RequestBody UserLoginBO userloginBO, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userloginBO.getUsername())) {
            return ApiJsonResult.errorMsg("用户名不能为空");
        }
        if (StringUtils.isBlank(userloginBO.getPassword())) {
            return ApiJsonResult.errorMsg("密码不能为空");
        }
        log.info("用户登入，user={}", JsonUtils.objectToJson(userloginBO));
        UserVO user = userService.checkLogin(userloginBO);
        if (user != null) {
            CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);
            return ApiJsonResult.ok(user);
        }
        return ApiJsonResult.errorMsg("用户名或密码错误");
    }
}
