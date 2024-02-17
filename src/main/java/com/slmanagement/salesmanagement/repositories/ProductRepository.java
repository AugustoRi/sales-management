package com.slmanagement.salesmanagement.repositories;

import com.slmanagement.salesmanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);

    @Query("Select prod" +
            " from Product prod" +
            " where prod.id = :id" +
            " and prod.category.id = :categoryId")
    Optional<Product> findByProductId(Long id, Long categoryId);

    Optional<Product> findByCategoryIdAndDescription(Long categoryId, String description);
}
