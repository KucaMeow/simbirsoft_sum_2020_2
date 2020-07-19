package ru.stepan.ponomarev.storage_project.model;

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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "metric_types")
@ApiModel(
        value = "MetricType",
        description = "Contains info about product metrics (kg, l, ...)"
)
public class MetricType {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(
            value = "ID",
            required = true,
            example = "Null, 1001"
    )
    Long id;
    /**
     * Metric type name shown to user
     */
    @Column(name = "metric_type")
    @ApiModelProperty(
            value = "Metric",
            required = true,
            example = "kilogram",
            notes = "Should be with length from 0 to 64"
    )
    String metric;
}
