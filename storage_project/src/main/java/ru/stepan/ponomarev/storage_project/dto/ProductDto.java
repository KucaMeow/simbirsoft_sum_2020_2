package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object for product
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(
        value = "ProductDto object",
        description = "Contains info about product without MetricType and ProductType objects, changed to ids"
)
public class ProductDto {
    @ApiModelProperty(
            value = "ID",
            required = true,
            example = "Null, 1001"
    )
    Long id;

    @ApiModelProperty(
            value = "Name",
            required = true,
            example = "Product name 1",
            notes = "Should be with length from 0 to 255"
    )
    String name;

    @ApiModelProperty(
            value = "Quantity",
            required = true,
            example = "100.1",
            notes = "Quantity of product in current single package"
    )
    double quantity;

    @ApiModelProperty(
            value = "ProductType ID",
            required = true,
            example = "1001",
            notes = "ID of ProductType object to map Product type to current Product"
    )
    long productTypeId;

    @ApiModelProperty(
            value = "MetricType ID",
            required = true,
            example = "1001",
            notes = "ID of MetricType object to map Metric type to current Product"
    )
    long metricTypeId;

    @ApiModelProperty(
            value = "Cost",
            required = true,
            example = "100.1",
            notes = "Cost of product in current single package"
    )
    double cost;
}
