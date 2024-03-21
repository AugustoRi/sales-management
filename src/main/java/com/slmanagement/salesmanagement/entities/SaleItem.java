package com.slmanagement.salesmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "item_venda")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "quantidade")
    private Integer quantity;

    @Column(name = "preco_vendido")
    private BigDecimal priceSold;

    @ManyToOne
    @JoinColumn(name = "codigo_produto", referencedColumnName = "codigo")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "codigo_venda", referencedColumnName = "codigo")
    private Sale sale;

    public SaleItem(Integer quantity, BigDecimal priceSold, Product product, Sale sale) {
        this.quantity = quantity;
        this.priceSold = priceSold;
        this.product = product;
        this.sale = sale;
    }
}
