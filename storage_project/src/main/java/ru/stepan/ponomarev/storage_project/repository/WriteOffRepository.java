package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.model.WriteOff;

import java.util.Date;
import java.util.List;

@Repository
public interface WriteOffRepository extends JpaRepository<WriteOff, Long> {
    List<WriteOff> getWriteOffsByTransactionDateBetween(Date transaction_date, Date transaction_date2);
}
