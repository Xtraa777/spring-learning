package com.xtraa.springlearning.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    // ResponseEntity 를 반환해서 상태코드, 헤더, 본문 모두 제어 가능
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        System.err.println("Global Error: Resourse not found -> " + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // @ExceptionHandler: 유효성 검사(@Valid) 실패 시 발생하는 MethodArgumentNotValidException 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        System.err.println("Global Error: Validation failed");

        Map<String, String> errors = new HashMap<>();
        // FieldError 로부터 필드 이름과 기본 메시지를 추출하여 Map 에 담음
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        System.err.println("Global Error: An unexpected error occurred -> " + ex.getMessage());
        ex.printStackTrace(); // 서버 로그에는 스택 트레이스를 남겨 디버깅에 활용

        // 500 Internal Server Error 상태 코드와 함께 일반적인 오류 메시지 반환
        // 사용자에게는 상세한 기술 정보 노출 X
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred.");
    }
}
