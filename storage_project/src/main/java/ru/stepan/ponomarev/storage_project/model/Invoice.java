package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Invoice object for adding products to storage
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoices")
public class Invoice {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Transaction object for order
     */
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    /**
     * Boolean value of confirmation of this invoice. True if confirmed
     */
    @Column(name = "is_confirmed")
    private boolean isConfirmed;
}
