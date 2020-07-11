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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    ProductType productType;
    @Column(name = "quantity")
    float quantity;
    @ManyToOne
    @JoinColumn(name = "metric_id")
    private MetricType metricType;
    @Column(name = "cost")
    float cost;

    public String getMetcicType() {
        return metricType.metric;
    }
}
