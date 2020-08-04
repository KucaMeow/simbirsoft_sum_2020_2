package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object for productInfo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(
        value = "Product Info Dto",
        description = "Dto object for productInfo"
)
public class ProductInfoDto {
    @ApiModelProperty(
            name = "Id"
    )
    private Long id;

    @ApiModelProperty(
            name = "Product Id",
            required = true
    )
    private Long productId;

    @ApiModelProperty(
            name = "Quantity",
            required = true
    )
    private int quantity;

    @ApiModelProperty(
            name = "Shop Id"
    )
    private Long shopId;

    @ApiModelProperty(
            name = "At storage",
            notes = "Should be true if shopId is Null. And should be false if shopId is not Null"
    )
    private boolean atStorage;
}
