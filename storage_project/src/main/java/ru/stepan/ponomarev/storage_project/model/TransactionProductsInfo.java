package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Additional products container (About the same as ProductInfo but for transactions)
 */
@Entity
@Table (name = "transaction_products_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionProductsInfo {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    /**
     * Product. Sets the product which is recorded in this package
     */
    @ManyToOne
    @JoinColumn (name = "product_id")
    Product product;

    /**
     * Quantity of units of product (not quantity of one unit!)
     */
    @Column(name = "quantity")
    int quantity;

    /**
     * Shop where this package of products is placed. Can be NULL if it's in storage
     */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    Shop shop;

    /**
     * Shows if this package is at storage or not. IT SHOULD BE TRUE IF IN STORAGE AND FALSE ONLY IF IN THE SHOP
     */
    @Column(name = "is_at_storage")
    boolean atStorage;

    /**
     * Transaction to which this package belongs
     */
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    Transaction transaction;

    /**
     * Snapshot of cost of current product
     */
    @Column(name = "current_cost")
    double currentCost;
}
