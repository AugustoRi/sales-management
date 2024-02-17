package com.slmanagement.salesmanagement.dtos.responses;

import com.slmanagement.salesmanagement.entities.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Product Response DTO")
public class ProductResponseDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "description")
    private String description;

    @ApiModelProperty(value = "quantity")
    private Integer quantity;

    @ApiModelProperty(value = "costPrice")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "sellingPrice")
    private BigDecimal sellingPrice;

    @ApiModelProperty(value = "observability")
    private String observability;

    @ApiModelProperty(value = "category")
    private CategoryResponseDTO category;

    public static ProductResponseDTO handleConvertToProductDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getDescription(),
                product.getQuantity(),
                product.getCostPrice(),
                product.getSellingPrice(),
                product.getObservability(),
                CategoryResponseDTO.handleConvertToCategoryDTO(product.getCategory())
        );
    }
}
