package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Kind of stock or package for products to be differed to units quantities and their location (shop or storage)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_infos")
public class ProductsInfo {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    /**
     * Product. Sets the product which is recorded in this package
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Quantity of units of product (not quantity of one unit!)
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Shop where this package of products is placed. Can be NULL if it's in storage
     */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    /**
     * Shows if this package is at storage or not. IT SHOULD BE TRUE IF IN STORAGE AND FALSE ONLY IF IN THE SHOP
     */
    @Column(name = "is_at_storage")
    private boolean atStorage;
}
