package com.mogakco.domain.member.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberVerifyCredentialsRequestDto(
        @Email(message = "이메일이 잘못되었습니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email
) {
}
