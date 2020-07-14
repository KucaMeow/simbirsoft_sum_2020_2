package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Class which describes transaction
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {
    /**
     * Id of record of object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    /**
     * Date of transaction creation
     */
    @Column(name = "timestamp")
    Date date;

    /**
     * List of products infos. They describes packages of products, which was bought and store, where it has bought
     */
    @OneToMany(mappedBy = "transaction")
    List<TransactionProductsInfo> productList;

    /**
     * Snapshot of sum of products costs at the time, transaction created
     */
    @Column(name = "cost_sum")
    double cost_sum;

}
