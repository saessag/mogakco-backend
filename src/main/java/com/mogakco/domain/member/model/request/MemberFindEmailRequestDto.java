package com.mogakco.domain.member.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberFindEmailRequestDto(
        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^(010|011)-\\d{4}-\\d{4}$",
                message = "전화번호는 010 또는 011로 시작하고, '-'로 구분되며 가운데와 끝은 각각 4자리 숫자여야 합니다.")
        String phoneNumber
) {
}
