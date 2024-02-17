package com.slmanagement.salesmanagement.dtos.responses;

import com.slmanagement.salesmanagement.entities.Client;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ApiModel("Client Response DTO")
public class ClientResponseDTO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "active")
    private Boolean active;

    @ApiModelProperty(value = "address")
    private AddressResponseDTO addressResponseDTO;

    public static ClientResponseDTO handleConvertToClientDTO(Client client) {

         AddressResponseDTO clientAddressResponseDTO = new AddressResponseDTO(
                client.getAddress().getAddress(),
                client.getAddress().getHouseNumber(),
                client.getAddress().getHouseObservation(),
                client.getAddress().getNeighborhood(),
                client.getAddress().getZipCode(),
                client.getAddress().getCity(),
                client.getAddress().getState()
        );

        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getActive(),
                clientAddressResponseDTO
        );
    }
}
