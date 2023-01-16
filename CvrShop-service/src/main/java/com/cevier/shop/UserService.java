package com.cevier.shop;

import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.passport.UserBO;
import com.cevier.shop.pojo.bo.passport.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;

public interface UserService {

    boolean checkIfUserNameExist(String userName);

    UserVO creatUser(UserBO userBO);

    UserVO checkLogin(UserLoginBO userloginBO);
}
