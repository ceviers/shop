package com.cevier.shop;

import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.UserBO;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;

public interface UserService {

    boolean checkIfUserNameExist(String userName);

    Users creatUser(UserBO userBO);

    Users checkLogin(UserLoginBO userloginBO);
}
