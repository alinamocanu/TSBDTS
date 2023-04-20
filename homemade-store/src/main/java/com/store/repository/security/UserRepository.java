package com.store.repository.security;

import com.store.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByUsername(String username);
    User findById(Long id);
    void deleteById(Long id);

    User findByUsername(String username);
}
