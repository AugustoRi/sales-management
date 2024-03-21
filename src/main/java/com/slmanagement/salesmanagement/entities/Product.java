package com.slmanagement.salesmanagement.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class Product {
    //  auto-increment primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "descricao")
    private String description;

    @Column(name = "quantidade")
    private Integer quantity;

    @Column(name = "preco_custo")
    private BigDecimal costPrice;

    @Column(name = "preco_venda")
    private BigDecimal sellingPrice;

    @Column(name = "observacao")
    private String observability;

    //  relationship between tables
    @ManyToOne
    @JoinColumn(name = "codigo_categoria", referencedColumnName = "codigo")
    @NotNull(message = "Id da categoria")
    //  foreign key
    private Category category;

    public Product(String description, Integer quantity, BigDecimal costPrice, BigDecimal sellingPrice, String observability, Category category) {
        this.description = description;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.observability = observability;
        this.category = category;
    }

    public Product(Long id) {
        this.id = id;
    }
}
