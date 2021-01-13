package com.example.odprojekt.repository;

import com.example.odprojekt.entity.ResetPasswordToken;
import com.example.odprojekt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, String> {
}
