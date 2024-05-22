package com.mogakco.global.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorResponse {

    // 회원 도메인 Error Response
    DUPLICATE_USER_NICKNAME(HttpStatus.BAD_REQUEST, "USER-ERROR-001", "이미 사용 중인 닉네임입니다.");

    private HttpStatus httpStatus;
    private String code;
    private String description;
}
