package com.mogakco.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberCreateRequest {

    @NotEmpty(message = "회원 이름은 필수 입력입니다.")
    private String name;

    @NotEmpty(message = "닉네임 입력은 필수 입력입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 최소 2글자, 최대 10글자까지 입력 가능합니다.")
    private String nickname;

    @NotEmpty(message = "회원 이메일은 필수 입력입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "비밀번호는 8글자 ~ 20글자의 영문 소문자, 숫자, 특수문자 조합이어야 합니다.")
    private String password;
}
