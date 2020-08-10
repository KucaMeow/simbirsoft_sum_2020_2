package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(
        value = "Order Dto",
        description = "Contains info about order"
)
public class OrderDto {

    @ApiModelProperty(
            value = "ID",
            example = "Null, 1001"
    )
    private Long id;

    @ApiModelProperty(
            value = "User ID",
            example = "Null, 1001"
    )
    private Long userId;

    @ApiModelProperty(
            value = "Product info Dto's list",
            required = true,
            example = "[{id:1, quantity:100}, {id:2, quantity:15}]"
    )
    private List<ProductInfoDto> productInfoDtos;

    @ApiModelProperty(
            value = "Transaction ID",
            example = "1, 10, Null"
    )
    private Long transactionId;

    @ApiModelProperty(
            value = "Status ID",
            example = "Null, 1001"
    )
    private Long statusId;

    @ApiModelProperty(
            value = "Is processed",
            notes = "Is true when order is processed"
    )
    private boolean isProcessed;
}
