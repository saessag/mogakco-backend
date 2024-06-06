package com.mogakco.domain.member.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberLoginRequestDto(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "유효한 이메일 주소를 입력해야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호는 8글자이상이며 적어도 1개 이상의 특수문자를 포함해야 합니다.")
        String password
) {
}
