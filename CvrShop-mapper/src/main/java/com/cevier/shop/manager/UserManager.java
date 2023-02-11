package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;

public interface UserManager extends IService<Users> {
    boolean checkIfUserNameExist(String userName);

    void saveUser(Users user);

    Users checkIfUserNameAndPasswordMatched(UserLoginBO userloginBO);
}
