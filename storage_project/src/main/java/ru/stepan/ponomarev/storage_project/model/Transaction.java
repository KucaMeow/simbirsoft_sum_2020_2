package ru.stepan.ponomarev.storage_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(name = "timestamp")
    Date date;

    @OneToMany(mappedBy = "transaction")
    List<TransactionProductsInfo> productList;

    @Column(name = "cost_sum")
    double cost_sum;

}
