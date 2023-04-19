package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orederItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "decorationId")
    private Decoration decoration;

    public OrderItem(int quantity, double price, Decoration decoration) {
        this.quantity = quantity;
        this.price = price;
        this.decoration = decoration;
    }
}
