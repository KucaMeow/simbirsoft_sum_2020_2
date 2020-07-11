package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepan.ponomarev.storage_project.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
