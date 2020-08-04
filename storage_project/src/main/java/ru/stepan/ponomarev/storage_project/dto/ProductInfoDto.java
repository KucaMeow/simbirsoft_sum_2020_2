package ru.stepan.ponomarev.storage_project.dto;

import io.swagger.annotations.ApiModel;
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

    private Long id;
    private Long productId;
    private int quantity;
    private Long shopId;
    private boolean atStorage;
}
