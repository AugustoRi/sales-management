package com.slmanagement.salesmanagement.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Sale Response DTO")
public class SaleResponseDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @ApiModelProperty(value = "Sales Items")
    private List<SaleItemResponseDTO> saleItemResponseDTOs;
}
