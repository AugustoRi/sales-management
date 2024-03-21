package com.slmanagement.salesmanagement.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Sale Item Request DTO")
public class SaleItemRequestDTO {
    @ApiModelProperty("productId")
    @NotNull(message = "Id Produto")
    private Long productId;

    @ApiModelProperty("quantity")
    @NotNull(message = "Quantidade")
    @Min(value = 1, message = "Quantidade")
    private Integer quantity;

    @ApiModelProperty("priceSold")
    @NotNull(message = "Preço Vendido")
    private BigDecimal priceSold;
}
