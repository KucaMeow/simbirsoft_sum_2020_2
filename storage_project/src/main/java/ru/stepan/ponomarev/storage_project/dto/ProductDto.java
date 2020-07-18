package ru.stepan.ponomarev.storage_project.dto;

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
public class ProductDto {
    Long id;
    String name;
    double quantity;
    long productTypeId;
    long metricTypeId;
    double cost;
}
