package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
