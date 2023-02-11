package com.cevier.shop.controller;

import com.cevier.shop.UserService;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.ShopcartBO;
import com.cevier.shop.pojo.bo.UserBO;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;
import com.cevier.shop.pojo.vo.UsersVO;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "注册与登入")
@Slf4j
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController{

    private static final String LOGIN_COOKIE_NAME = "user";

    @Resource
    private UserService userService;

    @Resource
    private RedisOperator redisOperator;

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
        Users user = userService.creatUser(userBO);

        // 实现用户的redis会话
        UsersVO usersVO = conventUsersVO(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(usersVO), true);
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
        Users user = userService.checkLogin(userloginBO);
        if (user != null) {
            // 实现用户的redis会话
            UsersVO usersVO = conventUsersVO(user);

            CookieUtils.setCookie(request, response, LOGIN_COOKIE_NAME, JsonUtils.objectToJson(usersVO), true);

            // 同步购物车数据
            synchShopcartData(user.getId(), request, response);


            return ApiJsonResult.ok(user);
        }
        return ApiJsonResult.errorMsg("用户名或密码错误");
    }

    @Operation(summary = "用户退出登录")
    @PostMapping("/logout")
    public ApiJsonResult logout(@RequestParam String userId,  HttpServletRequest request, HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, LOGIN_COOKIE_NAME);
        // 用户退出登录，清除redis中user的会话信息
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);
        // 分布式会话中需要清除用户数据
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        return ApiJsonResult.ok();
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void synchShopcartData(String userId, HttpServletRequest request,
                                   HttpServletResponse response) {

        /**
         * 1. redis中无数据，如果cookie中的购物车为空，那么这个时候不做任何处理
         *                 如果cookie中的购物车不为空，此时直接放入redis中
         * 2. redis中有数据，如果cookie中的购物车为空，那么直接把redis的购物车覆盖本地cookie
         *                 如果cookie中的购物车不为空，
         *                      如果cookie中的某个商品在redis中存在，
         *                      则以cookie为主，删除redis中的，
         *                      把cookie中的商品直接覆盖redis中（参考京东）
         * 3. 同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
         */

        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);

        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            // redis为空，cookie不为空，直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopcartStrCookie)) {

                /**
                 * 1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                 * 2. 该项商品标记为待删除，统一放入一个待删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */

                List<ShopcartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopcartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartJsonRedis, true);
            }

        }
    }
}
