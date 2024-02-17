package com.slmanagement.salesmanagement.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Address {
    @Column(name = "logradouro")
    private String address;

    @Column(name = "numero")
    private Integer houseNumber;

    @Column(name = "complemento")
    private String houseObservation;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "cep")
    private String zipCode;

    @Column(name = "cidade")
    private String city;

    @Column(name = "estado")
    private String state;
}
