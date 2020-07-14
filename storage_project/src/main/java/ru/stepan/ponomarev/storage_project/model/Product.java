package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepan.ponomarev.storage_project.dto.ProductDto;

import javax.persistence.*;

/**
 * Product in storage which can be placed. This class contains some common information about product.
 * Describe full set of products which can be sold by shops
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    /**
     * Name of product
     */
    @Column(name = "name")
    String name;

    /**
     * Quantity for one item (f.e. 1 of kilograms or 0,5 of liters)
     */
    @Column(name = "quantity")
    double quantity;

    /**
     * Type of product to differ products in groups
     */
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    ProductType productType;

    /**
     * Type of quantity metric to be displayed (f.e. kg or l for 1 of kilograms or 0,5 of liters structure)
     */
    @ManyToOne
    @JoinColumn(name = "metric_id")
    private MetricType metricType;

    /**
     * Current cost of product in rubbles (or other currency, depends on location and so on)
     */
    @Column(name = "cost")
    double cost;
}
