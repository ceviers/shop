package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.UserManager;
import com.cevier.shop.mapper.UsersMapper;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
public class UserManagerImpl extends ServiceImpl<UsersMapper, Users> implements UserManager {
    @Override
    public boolean checkIfUserNameExist(@NonNull String userName) {
        if (!StringUtils.hasText(userName)) {
            throw new RuntimeException("用户名不能为空");
        }
        return this.lambdaQuery().eq(Users::getUsername, userName).exists();
    }

    @Override
    public void saveUser(Users user) {
        this.save(user);
    }

    @Override
    public Users checkIfUserNameAndPasswordMatched(UserLoginBO userloginBO) {
        Users user = this.lambdaQuery().eq(Users::getUsername, userloginBO.getUsername())
                .eq(Users::getPassword, userloginBO.getPassword()).last("LIMIT 1").one();
//        UserVO userVO = new UserVO();
//        BeanUtils.copyProperties(user, userVO);
        return user;
    }
}
