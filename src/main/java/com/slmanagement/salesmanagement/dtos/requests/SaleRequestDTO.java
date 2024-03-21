package com.slmanagement.salesmanagement.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Sale Request DTO")
public class SaleRequestDTO {
    @ApiModelProperty(value = "date")
    @NotNull(message = "Data")
    private LocalDate date;

    @ApiModelProperty(value = "saleItems")
    @NotNull(message = "Itens Venda")
    @Valid
    private List<SaleItemRequestDTO> saleItemDTO;
}
