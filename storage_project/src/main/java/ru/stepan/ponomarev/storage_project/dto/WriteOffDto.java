package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto object for writing off
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(
        value = "Write-off Dto",
        description = "Dto object for writing off"
)
public class WriteOffDto {
    @ApiModelProperty(
            value = "ID",
            example = "Null, 1001"
    )
    private Long id;

    @ApiModelProperty(
            value = "Product info Dto's list",
            required = true,
            example = "[{id:1, quantity:100}, {id:2, quantity:15}]"
    )
    private List<ProductInfoDto> productInfoDtos;

    @ApiModelProperty(
            value = "Transaction id",
            example = "1, 10, Null"
    )
    private Long transactionId;

    @ApiModelProperty(
            value = "Id of shop",
            required = false,
            notes = "If invoice to storage, should be null. If it's invoice to shop, need to be not null"
    )
    private Long shopId;

    @ApiModelProperty(
            value = "Is confirmed",
            required = true,
            example = "true, false"
    )
    private boolean isConfirmed;
}
