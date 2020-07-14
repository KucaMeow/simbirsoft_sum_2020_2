package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name = "transaction_products_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionProductsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @ManyToOne
    @JoinColumn (name = "product_id")
    Product product;
    @Column(name = "quantity")
    int quantity;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    Shop shop;
    @Column(name = "is_at_storage")
    boolean atStorage;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    Transaction transaction;
    @Column(name = "current_cost")
    double currentCost;
}
