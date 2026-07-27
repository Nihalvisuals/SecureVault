package com.securevault.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securevault.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}