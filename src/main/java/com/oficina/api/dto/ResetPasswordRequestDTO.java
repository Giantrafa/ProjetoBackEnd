package com.oficina.api.dto;

public record ResetPasswordRequestDTO(String token, String newPassword) {}
