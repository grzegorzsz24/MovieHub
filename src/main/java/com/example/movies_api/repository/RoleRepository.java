package com.example.movies_api.repository;

import com.example.movies_api.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
