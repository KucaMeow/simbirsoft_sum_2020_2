package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Shop class
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shops")
public class Shop {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Name of branch
     */
    @Column(name = "shop_name")
    private String name;

    /**
     * Address of branch
     */
    @Column(name = "address")
    private String address;

    /**
     * List of products
     */
    @OneToMany(mappedBy = "shop")
    private List<ProductsInfo> products;
}
