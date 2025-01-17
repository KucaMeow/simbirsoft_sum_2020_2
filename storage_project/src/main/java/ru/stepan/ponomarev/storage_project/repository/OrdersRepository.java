package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.model.Order;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    public List<Order> findOrdersByCustomer_Username(String username);
}
