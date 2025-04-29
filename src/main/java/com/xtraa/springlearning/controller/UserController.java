package com.xtraa.springlearning.controller;

import com.xtraa.springlearning.dto.CreateUserReqDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserReqDto request) {

        // 만약 유효성 검사에 실패했다면, 이 메서드는 실행되지 않고
        // Spring이 자동으로 MethodArgumentNotValidException 예외를 발생시킵니다.
        // (기본적으로 400 Bad Request 응답 반환)

        // 유효성 검사를 통과했다면, 이제 request 객체는 안전하다고 간주하고 비즈니스 로직 수행
        System.out.println("Received valid user data:");
        System.out.println("Username: " + request.getUsername());
        System.out.println("Email: " + request.getEmail());
        // ... 사용자 생성 서비스 호출 등 ...

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
    }

    // 참고: 유효성 검사 오류 결과를 메서드 내에서 직접 받고 싶다면 @Valid 뒤에 BindingResult 파라미터 추가
    /*
    @PostMapping("/api/users_manual_validation")
    public ResponseEntity<?> createUserManual(@Valid @RequestBody CreateUserReqDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 있다면 bindingResult 에 오류 정보가 담김
            // 오류 정보를 추출하여 원하는 형식(예: Map<필드명, 오류메시지>)으로 응답 구성 가능
            // 예: return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        }

        // 유효성 검사 통과 시 비즈니스 로직 수행...
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
    }
     */
}
