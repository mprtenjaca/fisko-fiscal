package com.fisco.fiscal.fiskofiscal.repository;

import com.fisco.fiscal.fiskofiscal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
