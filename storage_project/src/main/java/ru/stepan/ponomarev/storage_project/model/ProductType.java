package ru.stepan.ponomarev.storage_project.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Product type to differ different types of products in the shop
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_types")
@ApiModel(
        value = "ProductType",
        description = "Contains info about product type/category/..."
)
public class ProductType {
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
     * Name of product category
     */
    @Column(name = "product_type")
    @ApiModelProperty(
            value = "Name",
            required = true,
            example = "Product category 1",
            notes = "Should be with length from 0 to 64"
    )
    String name;
}
