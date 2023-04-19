package com.store.repository;

import com.store.domain.Cart;
import com.store.domain.Customer;
import com.store.dto.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByCustomer(Customer customer);

    Cart findCartByCartId(Long id);

    List<Cart> findAll();
}
