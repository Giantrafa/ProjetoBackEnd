package com.oficina.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oficina.api.model.PasswordResetTokenModel;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenModel, Long> {
    Optional<PasswordResetTokenModel> findByToken(String token);

    @Transactional
    void deleteByEmail(String email);
}
