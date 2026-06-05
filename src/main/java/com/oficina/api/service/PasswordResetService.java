package com.oficina.api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.oficina.api.dto.ForgotPasswordRequestDTO;
import com.oficina.api.dto.ResetPasswordRequestDTO;
import com.oficina.api.model.PasswordResetTokenModel;
import com.oficina.api.repository.PasswordResetTokenRepository;
import com.oficina.api.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PasswordResetService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${app.frontend-url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${app.mail.from:noreply@autoshoppro.com}")
    private String mailFrom;

    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO request) {
        if (request == null || request.email() == null || request.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o e-mail.");
        }

        String email = request.email().trim().toLowerCase();

        // Silently succeed if email not found — prevents user enumeration
        if (usuarioRepository.findByEmail(email).isEmpty()) {
            return;
        }

        tokenRepository.deleteByEmail(email);

        PasswordResetTokenModel resetToken = new PasswordResetTokenModel();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setEmail(email);
        resetToken.setExpiresAt(LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        String resetLink = frontendUrl + "/redefinir-senha?token=" + resetToken.getToken();
        sendResetEmail(email, resetLink);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO request) {
        if (request == null || request.token() == null || request.newPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token e nova senha sao obrigatorios.");
        }

        if (request.newPassword().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve ter no minimo 8 caracteres.");
        }

        PasswordResetTokenModel resetToken = tokenRepository.findByToken(request.token())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido ou expirado."));

        if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido ou expirado.");
        }

        var usuario = usuarioRepository.findByEmail(resetToken.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido ou expirado."));

        usuario.setPassword(passwordEncoder.encode(request.newPassword()));
        usuarioRepository.save(usuario);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

    private void sendResetEmail(String to, String resetLink) {
        if (mailSender == null) {
            log.warn("SMTP nao configurado — link de recuperacao para {}: {}", to, resetLink);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(to);
            message.setSubject("Recuperar senha - AutoShop Pro");
            message.setText(
                "Recebemos uma solicitacao de recuperacao de senha para sua conta.\n\n" +
                "Clique no link abaixo para criar uma nova senha (valido por 1 hora):\n\n" +
                resetLink + "\n\n" +
                "Se voce nao solicitou isso, ignore este e-mail.\n\n" +
                "AutoShop Pro"
            );
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Falha ao enviar e-mail de recuperacao para {}: {}", to, e.getMessage());
        }
    }
}
