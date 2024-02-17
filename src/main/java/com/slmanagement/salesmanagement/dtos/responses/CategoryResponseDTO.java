package com.slmanagement.salesmanagement.dtos.responses;

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
@ApiModel("Category Response DTO")
public class CategoryResponseDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @NotBlank(message = "Nome")
    @Length(min = 3, max = 50, message = "Nome")
    @ApiModelProperty(value = "name")
    private String name;

    public static CategoryResponseDTO handleConvertToCategoryDTO(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}
