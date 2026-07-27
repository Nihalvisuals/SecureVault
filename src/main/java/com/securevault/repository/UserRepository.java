package com.securevault.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securevault.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}