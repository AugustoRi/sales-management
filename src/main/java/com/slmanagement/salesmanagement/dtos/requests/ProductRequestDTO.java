package com.slmanagement.salesmanagement.dtos.requests;

import com.slmanagement.salesmanagement.entities.Category;
import com.slmanagement.salesmanagement.entities.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Product Request DTO")
public class ProductRequestDTO {

    @ApiModelProperty(value = "description")
    @NotBlank(message = "Descrição")
    @Length(min = 3, max = 100, message = "Descrição")
    private String description;

    @ApiModelProperty(value = "quantity")
    @NotNull(message = "Quantidade")
    private Integer quantity;

    @ApiModelProperty(value = "costPrice")
    @NotNull(message = "Preço Custo")
    private BigDecimal costPrice;

    @ApiModelProperty(value = "sellingPrice")
    @NotNull(message = "Preço Venda")
    private BigDecimal sellingPrice;

    @ApiModelProperty(value = "observability")
    @NotBlank(message = "Observação")
    @Length(max = 500, message = "Observação")
    private String observability;

    public Product handleConvertToEntity(Long categoryId) {
        return new Product(
                description,
                quantity,
                costPrice,
                sellingPrice,
                observability,
                new Category(categoryId)
        );
    }

    public Product handleConvertToEntity(Long id, Long categoryId) {
        return new Product(
                id,
                description,
                quantity,
                costPrice,
                sellingPrice,
                observability,
                new Category(categoryId)
        );
    }

}
