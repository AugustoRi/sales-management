package com.slmanagement.salesmanagement.services.abstracts;

import com.slmanagement.salesmanagement.dtos.requests.SaleItemRequestDTO;
import com.slmanagement.salesmanagement.dtos.responses.ClientSaleResponseDTO;
import com.slmanagement.salesmanagement.dtos.responses.SaleItemResponseDTO;
import com.slmanagement.salesmanagement.dtos.responses.SaleResponseDTO;
import com.slmanagement.salesmanagement.entities.Product;
import com.slmanagement.salesmanagement.entities.Sale;
import com.slmanagement.salesmanagement.entities.SaleItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSaleService {
    protected SaleResponseDTO handleCreateSaleResponseDTO(Sale sale, List<SaleItem> saleItemList) {
        List<SaleItemResponseDTO> handleGetSaleItemResponseDTO = saleItemList
                .stream()
                .map(this::handleCreateSaleItemResponseDTO)
                .collect(Collectors.toList());

        return new SaleResponseDTO(
                sale.getId(),
                sale.getDate(),
                handleGetSaleItemResponseDTO
        );
    }

    protected SaleItemResponseDTO handleCreateSaleItemResponseDTO(SaleItem saleItem) {
        return new SaleItemResponseDTO(
                saleItem.getId(),
                saleItem.getQuantity(),
                saleItem.getPriceSold(),
                saleItem.getProduct().getId(),
                saleItem.getProduct().getDescription()
        );
    }

    protected ClientSaleResponseDTO handleReturnClientSaleResponseDTO(Sale sale, List<SaleItem> saleItemsList) {
        return new ClientSaleResponseDTO(
                sale.getClient().getName(),
                Arrays.asList(
                        handleCreateSaleResponseDTO(sale, saleItemsList)
                )
        );
    }

    protected SaleItem handleCreateSaleItem(SaleItemRequestDTO saleItemDto, Sale sale) {
        return new SaleItem(
                saleItemDto.getQuantity(),
                saleItemDto.getPriceSold(),
                new Product(saleItemDto.getProductId()),
                sale
        );
    }
}
