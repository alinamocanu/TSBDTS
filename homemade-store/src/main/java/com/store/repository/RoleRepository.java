package com.store.repository;

import com.store.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleId(Long id);
}
