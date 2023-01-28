package com.cevier.shop.impl.center;

import com.cevier.shop.center.CenterUserService;
import com.cevier.shop.manager.UserManager;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.center.CenterUserBO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Resource
    private UserManager userManager;

    @Override
    public Users queryUserInfo(String userId) {
        Users user = userManager.getById(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());

        userManager.updateById(updateUser);

        return queryUserInfo(userId);
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());

        userManager.updateById(updateUser);

        return queryUserInfo(userId);
    }
}
