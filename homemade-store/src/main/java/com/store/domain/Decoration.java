package com.store.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "decorations")
public class Decoration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long decorationId;

    @Column(name = "decorationName")
    private String decorationName;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private DecorationCategory category;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "decoration", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Lob
    private Byte[] image;
}
