package com.slmanagement.salesmanagement.repositories;

import com.slmanagement.salesmanagement.entities.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    @Query("select new com.slmanagement.salesmanagement.entities.SaleItem(" +
            " iv.id, iv.quantity, iv.priceSold, iv.product, iv.sale)" +
            " from SaleItem iv" +
            " where iv.sale.id = :saleId")
    List<SaleItem> findBySalePerId(Long saleId);
}
