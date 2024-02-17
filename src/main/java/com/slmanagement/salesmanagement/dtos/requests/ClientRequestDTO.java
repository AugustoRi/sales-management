package com.slmanagement.salesmanagement.dtos.requests;

import com.slmanagement.salesmanagement.dtos.responses.AddressResponseDTO;
import com.slmanagement.salesmanagement.dtos.responses.ClientResponseDTO;
import com.slmanagement.salesmanagement.entities.Address;
import com.slmanagement.salesmanagement.entities.Client;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Client Request DTO")
public class ClientRequestDTO {
    @ApiModelProperty(value = "name")
    @NotBlank(message = "Nome")
    @Length(min = 3, max = 50, message = "Nome")
    private String name;

    @ApiModelProperty(value = "phone")
    @NotBlank(message = "Telefone")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{5}[- .]\\d{4}", message = "Telefone")
    private String phone;

    @ApiModelProperty(value = "active")
    @NotNull(message = "Ativo")
    private Boolean active;

    @ApiModelProperty(value = "address")
    @NotNull(message = "Endereço")
    @Valid
    private AddressRequestDTO addressRequestDTO;

    public Client handleConvertToEntity() {
        return new Client(
                name,
                phone,
                active,
                new Address(
                        addressRequestDTO.getAddress(),
                        addressRequestDTO.getHouseNumber(),
                        addressRequestDTO.getHouseObservation(),
                        addressRequestDTO.getNeighborhood(),
                        addressRequestDTO.getZipCode(),
                        addressRequestDTO.getCity(),
                        addressRequestDTO.getState()
                )
        );
    }

    public Client handleConvertToEntity(Long id) {
        return new Client(
                id,
                name,
                phone,
                active,
                new Address(
                        addressRequestDTO.getAddress(),
                        addressRequestDTO.getHouseNumber(),
                        addressRequestDTO.getHouseObservation(),
                        addressRequestDTO.getNeighborhood(),
                        addressRequestDTO.getZipCode(),
                        addressRequestDTO.getCity(),
                        addressRequestDTO.getState()
                )
        );
    }
}
