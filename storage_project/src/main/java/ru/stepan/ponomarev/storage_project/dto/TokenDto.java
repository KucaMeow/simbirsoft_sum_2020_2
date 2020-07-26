package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "TokenDto",
        description = "Dto object for jwt token")
public class TokenDto {
    @ApiModelProperty(
            name = "Token",
            notes = "JWT token, generated on server for authentication"
    )
    String token;
}
