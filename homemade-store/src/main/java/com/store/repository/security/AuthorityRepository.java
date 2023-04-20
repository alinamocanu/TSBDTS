package com.store.repository.security;

import com.store.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findAuthorityByRole(String role);
}
