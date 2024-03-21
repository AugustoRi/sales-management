package com.slmanagement.salesmanagement.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Sale Item Response DTO")
public class SaleItemResponseDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "quantity")
    private Integer quantity;

    @ApiModelProperty(value = "priceSold")
    private BigDecimal priceSold;

    @ApiModelProperty(value = "productId")
    private Long productId;

    @ApiModelProperty(value = "productDescription")
    private String productDescription;
}
