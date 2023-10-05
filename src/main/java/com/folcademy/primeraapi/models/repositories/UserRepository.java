package com.folcademy.primeraapi.models.repositories;

import com.folcademy.primeraapi.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    default Collection<UserEntity> findAllByNameAndSurname(String name, String surname) {
        return null;
    }

    Boolean existsByEmail(String email);

    Optional<UserEntity> findOneByEmail(String email);
}
