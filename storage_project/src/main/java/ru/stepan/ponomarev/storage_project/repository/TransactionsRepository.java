package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.model.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
