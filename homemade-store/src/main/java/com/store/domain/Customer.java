package com.store.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
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

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_customers", joinColumns = @JoinColumn(name = "customerId", referencedColumnName = "customerId"),
            inverseJoinColumns = @JoinColumn(name="roleId", referencedColumnName = "roleId"))
    private Set<Role> roles;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        if(roles.size() == 0){
//            return Collections.emptyList();
//        }
//
//        roles.forEach(r -> {
//            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
//        });
//
//        return authorities;
//    }

    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean accountNotExpired = true;

    @Builder.Default
    private Boolean accountNotLocked = true;

    @Builder.Default
    private Boolean credentialsNotExpired = true;

//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
    public Customer(String email, String password, String address, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

