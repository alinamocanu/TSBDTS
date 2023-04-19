package com.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @Column(name = "cardNumber")
    private String cardNumber;

    @Column(name = "bankAccountCVV")
    private int bankAccountCVV;

    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccount")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
