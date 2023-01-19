package com.cevier.shop.impl;

import com.cevier.shop.UserService;
import com.cevier.shop.enums.GenderEnum;
import com.cevier.shop.manager.UserManager;
import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.UserBO;
import com.cevier.shop.pojo.bo.UserLoginBO;
import com.cevier.shop.pojo.vo.UserVO;
import com.cevier.shop.utils.DateUtil;
import com.cevier.shop.utils.MD5Utils;
import jakarta.annotation.Resource;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserManager userManager;

    @Resource
    private Sid sid;

    /**
     * 默认头像
     */
    private static final String USER_FACE = "https://cevier.com/img/logo.ico";

    @Override
    public boolean checkIfUserNameExist(String userName) {
        return userManager.checkIfUserNameExist(userName);
    }

    @Override
    @Transactional
    public UserVO creatUser(UserBO userBO) {
        Users user = new Users();

        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 设置默认值
        // 默认与用户名相同
        user.setNickname(userBO.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1970-01-01"));
        user.setSex(GenderEnum.UNKNOWN.getType());

        user.setId(sid.nextShort());
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        userManager.saveUser(user);

        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }


    @Override
    public UserVO checkLogin(UserLoginBO userloginBO) {
        try {
            userloginBO.setPassword(MD5Utils.getMD5Str(userloginBO.getPassword()));
        } catch (Exception e) {
            return null;
        }
        return userManager.checkIfUserNameAndPasswordMatched(userloginBO);
    }
}
