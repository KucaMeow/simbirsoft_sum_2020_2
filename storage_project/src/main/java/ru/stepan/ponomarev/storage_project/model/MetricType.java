package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Metric type of product quantity (for example it can be kg as kilogram
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "metric_types")
public class MetricType {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    /**
     * Metric type name shown to user
     */
    @Column(name = "metric_type")
    String metric;
}
