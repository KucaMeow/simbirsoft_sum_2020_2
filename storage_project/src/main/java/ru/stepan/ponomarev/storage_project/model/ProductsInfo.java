package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product_infos")
public class ProductsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @Column(name = "quantity")
    int quantity;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    Shop shop;
    @Column(name = "is_at_storage")
    boolean atStorage;
}
