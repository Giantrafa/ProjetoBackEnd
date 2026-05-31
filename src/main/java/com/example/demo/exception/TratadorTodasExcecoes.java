package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorTodasExcecoes {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(construirCorpoErro(ex.getMessage(), 404));
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Map<String, Object>> tratarRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(construirCorpoErro(ex.getMessage(), 422));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> errosCampos = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            errosCampos.put(erro.getField(), erro.getDefaultMessage());
        }
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("momento", LocalDateTime.now());
        corpo.put("status", 400);
        corpo.put("mensagem", "Dados inválidos na requisição");
        corpo.put("erros", errosCampos);
        return ResponseEntity.badRequest().body(corpo);
    }

    private Map<String, Object> construirCorpoErro(String mensagem, int status) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("momento", LocalDateTime.now());
        corpo.put("status", status);
        corpo.put("mensagem", mensagem);
        return corpo;
    }
}
