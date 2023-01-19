package com.cevier.shop;

import com.cevier.shop.pojo.bo.UserBO;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;

public interface UserService {

    boolean checkIfUserNameExist(String userName);

    UserVO creatUser(UserBO userBO);

    UserVO checkLogin(UserLoginBO userloginBO);
}
