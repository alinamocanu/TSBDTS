package com.store.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "customers")
public class Customer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name ="email")
    private String email;

    @Column(name ="username", unique = true)
    private String username;

    @Column(name ="password")
    private String password;

    private Integer enabled;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "address")
    private String address;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BankAccount> bankAccounts;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;


    public Customer(String email, String password, String address, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

