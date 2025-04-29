package com.xtraa.springlearning.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreateUserReqDto {

    @NotBlank(message = "사용자 이름은 필수이며 공백일 수 없습니다.") // null 아니고 공백 아닌 문자열
    @Size(min = 2, max = 20, message = "사용자 이름은 2자 이상 20자 이하여야 합니다.") // 길이 제약
    private String username;

    @NotBlank(message = "비밀번호는 필수이며 공백일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Email(message = "유효한 이메일 형식이 아닙니다.") // 이메일 형식 검사
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Min(value = 1, message = "나이는 최소 1세 이상이어야 합니다.") // 최소값 제약
    @Max(value = 120, message = "나이는 최대 120세 이하여야 합니다.") // 최대값 제약
    private int age; // int 형은 @NotNull 대신 @Min/Max 등으로 필수성 및 범위 검사

    @NotNull(message = "약관 동의 여부는 필수입니다.")
    // null 이 아니어야 함 (boolean 같은 primitive 타입에는 불필요하지만, Boolean 같은 래퍼 클래스에는 사용 가능)
    private Boolean agreedTerms;
}
