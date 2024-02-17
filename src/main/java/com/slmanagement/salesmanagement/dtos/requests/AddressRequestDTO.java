package com.slmanagement.salesmanagement.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Address Request DTO")
public class AddressRequestDTO {
    @ApiModelProperty(value = "address")
    @NotBlank(message = "Logradouro")
    @Length(min = 3, max = 30, message = "Logradouro")
    private String address;

    @ApiModelProperty(value = "houseNumber")
    @NotNull(message = "Número")
    private Integer houseNumber;

    @ApiModelProperty(value = "houseObservation")
    @Length(max = 30, message = "Complemento")
    private String houseObservation;

    @ApiModelProperty(value = "neighborhood")
    @NotBlank(message = "Bairro")
    @Length(min = 3, max = 30, message = "Bairro")
    private String neighborhood;

    @ApiModelProperty(value = "zipCode")
    @NotBlank(message = "CEP")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP")
    private String zipCode;

    @ApiModelProperty(value = "city")
    @NotBlank(message = "Cidade")
    @Length(min = 3, max = 30, message = "Cidade")
    private String city;

    @ApiModelProperty(value = "state")
    @NotBlank(message = "Estado")
    @Length(min = 3, max = 30, message = "Estado")
    private String state;
}
