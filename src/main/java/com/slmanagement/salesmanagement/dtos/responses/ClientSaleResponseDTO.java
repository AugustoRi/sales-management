package com.slmanagement.salesmanagement.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Sale Customer Response DTO")
public class ClientSaleResponseDTO {
    @ApiModelProperty(value = "Client Name")
    private String name;

    @ApiModelProperty(value = "Sales")
    private List<SaleResponseDTO> saleResponseDTOs;
}
