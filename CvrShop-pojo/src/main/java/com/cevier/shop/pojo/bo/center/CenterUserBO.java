package com.cevier.shop.pojo.bo.center;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "从客户端，由用户传入的数据封装在此entity中")
public class CenterUserBO {

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String password;
    @Schema(description = "确认密码", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String confirmPassword;


    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 12, message = "用户昵称不能超过12位")
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String nickname;

    @Size(max = 12, message = "用户真实姓名不能超过12位")
    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String realname;

    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mobile;

    @Email
    @Schema(description = "邮箱地址", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    @Schema(description = "性别", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer sex;
    @Schema(description = "生日", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date birthday;

}