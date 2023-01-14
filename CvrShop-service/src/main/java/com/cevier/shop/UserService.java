package com.cevier.shop;

import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.passport.UserBO;

public interface UserService {

    boolean checkIfUserNameExist(String userName);

    Users creatUser(UserBO userBO);
}
