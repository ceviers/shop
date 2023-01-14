package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Users;

public interface UserManager extends IService<Users> {
    boolean checkIfUserNameExist(String userName);

    void saveUser(Users user);
}
