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
    @Column(name = "name")
    String name;
    @Column(name = "quantity")
    double quantity;
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    ProductType productType;
    @ManyToOne
    @JoinColumn(name = "metric_id")
    private MetricType metricType;
    @Column(name = "cost")
    double cost;

    public String getMetcicType() {
        return metricType.metric;
    }
}
