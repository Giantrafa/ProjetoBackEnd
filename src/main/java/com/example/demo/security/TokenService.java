package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.model.UsuarioModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final String secret;
    private final long expirationHours;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TokenService(
        @Value("${api.security.jwt.secret}") String secret,
        @Value("${api.security.jwt.expiration-hours}") long expirationHours
    ) {
        this.secret = secret;
        this.expirationHours = expirationHours;
    }

    public String gerarToken(UsuarioModel usuario) {
        Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", usuario.getEmail());
        payload.put("nome", usuario.getNome());
        payload.put("perfil", usuario.getPerfil().name());
        payload.put("exp", Instant.now().plus(expirationHours, ChronoUnit.HOURS).getEpochSecond());

        String headerEncoded = encodeJson(header);
        String payloadEncoded = encodeJson(payload);
        String unsignedToken = headerEncoded + "." + payloadEncoded;

        return unsignedToken + "." + assinar(unsignedToken);
    }

    public boolean tokenValido(String token) {
        try {
            String[] partes = token.split("\\.");
            if (partes.length != 3) {
                return false;
            }

            String unsignedToken = partes[0] + "." + partes[1];
            if (!assinar(unsignedToken).equals(partes[2])) {
                return false;
            }

            Number exp = (Number) lerPayload(token).get("exp");
            return exp.longValue() > Instant.now().getEpochSecond();
        } catch (Exception exception) {
            return false;
        }
    }

    public String getEmail(String token) {
        return (String) lerPayload(token).get("sub");
    }

    public String getPerfil(String token) {
        return (String) lerPayload(token).get("perfil");
    }

    private String encodeJson(Map<String, Object> dados) {
        try {
            byte[] json = objectMapper.writeValueAsBytes(dados);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(json);
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel gerar o token.", exception);
        }
    }

    private Map<String, Object> lerPayload(String token) {
        try {
            String payload = token.split("\\.")[1];
            byte[] json = Base64.getUrlDecoder().decode(payload);
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception exception) {
            throw new IllegalArgumentException("Token invalido.", exception);
        }
    }

    private String assinar(String dados) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(key);
            byte[] assinatura = mac.doFinal(dados.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(assinatura);
        } catch (Exception exception) {
            throw new IllegalStateException("Nao foi possivel assinar o token.", exception);
        }
    }
}
