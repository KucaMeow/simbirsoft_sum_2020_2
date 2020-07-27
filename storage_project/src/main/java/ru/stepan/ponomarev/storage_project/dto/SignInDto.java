package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SignInDto",
        description = "Dto object for sign in info")
public class SignInDto {
    @ApiModelProperty(
            name = "Username",
            required = true,
            example = "SomeUserName"
    )
    private String username;
    @ApiModelProperty(
            name = "Password",
            required = true,
            example = "SomePassWord123"
    )
    private String password;
}
