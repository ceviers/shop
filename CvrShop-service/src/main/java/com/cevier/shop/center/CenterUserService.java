package com.cevier.shop.center;

import com.cevier.shop.pojo.Users;
import com.cevier.shop.pojo.bo.center.CenterUserBO;

public interface CenterUserService {
    Users queryUserInfo(String userId);

    Users updateUserFace(String userId, String faceUrl);

    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
