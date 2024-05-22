package com.mogakco.global.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessResponse {

    // 회원 도메인 Error Response
    SUCCESS_CREATE_USER(HttpStatus.BAD_REQUEST, "USER-RESULT-001", "이미 사용 중인 닉네임입니다.");

    private HttpStatus httpStatus;
    private String code;
    private String description;
}
