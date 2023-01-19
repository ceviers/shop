package com.cevier.shop.controller;

import com.cevier.shop.AddressService;
import com.cevier.shop.pojo.UserAddress;
import com.cevier.shop.pojo.bo.AddressBO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.MobileEmailUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "收货地址相关的api接口")
@RequestMapping("/address")
@RestController
public class AddressController {

    @Resource
    private AddressService addressService;

    @Operation(summary = "根据用户id查询收货地址列表")
    @PostMapping("/list")
    public ApiJsonResult list(
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAll(userId);
        return ApiJsonResult.ok(list);
    }

    @Operation(summary = "用户新增地址")
    @PostMapping("/add")
    public ApiJsonResult add(@RequestBody AddressBO addressBO) {

        ApiJsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.addNewUserAddress(addressBO);

        return ApiJsonResult.ok();
    }
    private ApiJsonResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return ApiJsonResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return ApiJsonResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return ApiJsonResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return ApiJsonResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return ApiJsonResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return ApiJsonResult.errorMsg("收货地址信息不能为空");
        }

        return ApiJsonResult.ok();
    }

    @Operation(summary = "用户修改地址")
    @PostMapping("/update")
    public ApiJsonResult update(@RequestBody AddressBO addressBO) {

        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return ApiJsonResult.errorMsg("修改地址错误：addressId不能为空");
        }

        ApiJsonResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.updateUserAddress(addressBO);

        return ApiJsonResult.ok();
    }

    @Operation(summary = "用户删除地址")
    @PostMapping("/delete")
    public ApiJsonResult delete(
            @RequestParam String userId,
            @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiJsonResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId, addressId);
        return ApiJsonResult.ok();
    }

    @Operation(summary = "用户设置默认地址")
    @PostMapping("/setDefalut")
    public ApiJsonResult setDefalut(
            @RequestParam String userId,
            @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiJsonResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return ApiJsonResult.ok();
    }
}
