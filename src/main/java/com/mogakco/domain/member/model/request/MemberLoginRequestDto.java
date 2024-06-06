package com.mogakco.domain.member.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberLoginRequestDto(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "이메일 혹은 비밀번호가 잘못되었습니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "이메일 혹은 비밀번호가 잘못되었습니다.")
        String password
) {
}
