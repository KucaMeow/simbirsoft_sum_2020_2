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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    ProductType productType;
    @Column(name = "quantity")
    float quantity;
    @Column(name = "metric_type")
    MetricType metricType;
    @Column(name = "cost")
    float cost;
}
