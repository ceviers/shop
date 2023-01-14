package com.cevier.shop.pojo.bo.passport;

import lombok.Data;

@Data
public class UserBO {
    private String username;

    private String password;

    private String confirmPassword;
}
