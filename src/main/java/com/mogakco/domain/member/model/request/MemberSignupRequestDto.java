package com.mogakco.domain.member.model.request;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.global.exception.custom.BusinessException;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public record MemberSignupRequestDto(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        @Size(min = 2, message = "이름은 최소 2글자 이상이어야 합니다.")
        String name,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(max = 8, message = "닉네임은 최대 8글자까지 가능합니다.")
        String nickname,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "유효한 이메일 주소를 입력해야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호는 8글자이상이며 적어도 1개 이상의 특수문자를 포함해야 합니다.")
        String password,

        @NotBlank(message = "비밀번호(확인)은 필수 입력 값입니다.")
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
                message = "비밀번호를 재입력해야 합니다.")
        String confirmPassword,

        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(010|011)-\\d{4}-\\d{4}$",
                message = "전화번호는 010 또는 011로 시작하고, '-'로 구분되며 가운데와 끝은 각각 4자리 숫자여야 합니다.")
        String phoneNumber,

        @NotNull(message = "생일은 필수 입력 값입니다.")
        @PastOrPresent(message = "생일은 오늘 날짜 이전이어야 합니다.")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthday
) {
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .birthday(birthday)
                .build();
    }

    public void validatePasswordAndConfirmPassword() {
        if (!password.equals(confirmPassword)) {
            throw new BusinessException("입력하신 비밀번호와 비밀번호(확인)이 일치하지 않습니다.");
        }
    }
}
