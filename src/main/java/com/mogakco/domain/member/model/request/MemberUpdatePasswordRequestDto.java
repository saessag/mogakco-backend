package com.mogakco.domain.member.model.request;

import com.mogakco.global.exception.custom.BusinessException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdatePasswordRequestDto(
        @Size(max = 6)
        @NotBlank(message = "인증코드가 공란일 수 없습니다.")
        String certificationNumber,

        @Email(message = "잘못된 이메일입니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호는 8글자이상이며 적어도 1개 이상의 특수문자를 포함해야 합니다.")
        String password,

        @NotBlank(message = "비밀번호(확인)은 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호를 재입력해야 합니다.")
        String confirmPassword
) {
    public void validatePasswordAndConfirmPassword() {
        if (!password.equals(confirmPassword)) {
            throw new BusinessException("입력하신 비밀번호와 비밀번호(확인)이 일치하지 않습니다.");
        }
    }
}
