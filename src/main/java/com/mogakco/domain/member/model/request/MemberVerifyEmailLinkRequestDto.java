package com.mogakco.domain.member.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberVerifyEmailLinkRequestDto(
        @Size(max = 6)
        @NotBlank(message = "인증코드가 공란일 수 없습니다.")
        String certificationNumber,

        @Email(message = "잘못된 이메일입니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email
) {
}
