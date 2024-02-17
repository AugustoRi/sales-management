package com.slmanagement.salesmanagement.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Address Response DTO")
public class AddressResponseDTO {
    @ApiModelProperty(value = "address")
    private String address;

    @ApiModelProperty(value = "houseNumber")
    private Integer houseNumber;

    @ApiModelProperty(value = "houseObservation")
    private String houseObservation;

    @ApiModelProperty(value = "neighborhood")
    private String neighborhood;

    @ApiModelProperty(value = "zipCode")
    private String zipCode;

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "state")
    private String state;
}
