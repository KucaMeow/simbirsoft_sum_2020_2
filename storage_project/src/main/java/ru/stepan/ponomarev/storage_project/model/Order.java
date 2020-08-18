package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Orders of users purchases
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * User account - owner of order
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    /**
     * Transaction object for order
     */
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    /**
     * Status of order
     */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;

    /**
     * Is true when order is processed and ready to deliver
     */
    @Column(name = "is_processed")
    private boolean processed;
}
