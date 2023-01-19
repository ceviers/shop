package com.cevier.shop.pojo.bo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserBO {
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmPassword;
}
