package com.store.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;



import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.enabled;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY)
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "customerId", referencedColumnName = "customerId"),
            inverseJoinColumns = @JoinColumn(name="roleId", referencedColumnName = "roleId"))
    private List<Role> roles;

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNotExpired = true;

    @Builder.Default
    private Boolean accountNotLocked = true;

    @Builder.Default
    private Boolean credentialsNotExpired = true;
}
