package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Metric type of product quantity (for example it can be kg as kilogram
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(
        value = "MetricType",
        description = "Contains info about product metrics (kg, l, ...)"
)
public class MetricTypeDto {
    /**
     * Id of record of object
     */
    @ApiModelProperty(
            value = "ID",
            required = true,
            example = "Null, 1001"
    )
    private Long id;
    /**
     * Metric type name shown to user
     */
    @ApiModelProperty(
            value = "Metric",
            required = true,
            example = "kilogram",
            notes = "Should be with length from 0 to 64"
    )
    private String metric;
}
