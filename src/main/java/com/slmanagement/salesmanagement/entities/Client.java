package com.slmanagement.salesmanagement.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "ativo")
    private Boolean active;

    @Embedded
    private Address address;

    public Client(String name, String phone, Boolean active, Address address) {
        this.name = name;
        this.phone = phone;
        this.active = active;
        this.address = address;
    }
}
