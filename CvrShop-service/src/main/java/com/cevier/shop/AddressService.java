package com.cevier.shop;

import com.cevier.shop.pojo.UserAddress;
import com.cevier.shop.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void deleteUserAddress(String userId, String addressId);

    void updateUserAddressToBeDefault(String userId, String addressId);
}
