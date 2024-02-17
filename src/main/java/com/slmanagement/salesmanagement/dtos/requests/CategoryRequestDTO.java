package com.slmanagement.salesmanagement.dtos.requests;

import com.slmanagement.salesmanagement.entities.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Category Request DTO")
public class CategoryRequestDTO {

    @ApiModelProperty(value = "name")
    @NotBlank(message = "Nome")
    @Length(min = 3, max = 50, message = "Nome")
    private String name;

    public Category handleConvertToEntity() {
        return new Category(name);
    }

    public Category handleConvertToEntity(Long id) {
        return new Category(id, name);
    }
}
