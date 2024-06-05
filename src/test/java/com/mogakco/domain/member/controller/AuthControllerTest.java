package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.global.controller.BaseControllerTest;
import com.mogakco.global.exception.GlobalExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {

    @Test
    @DisplayName("회원가입 통합 테스트 - 실패(잘못된 입력값)")
    void member_signup_integration_test_fail_caused_by_wrong_input() throws Exception {
        MemberSignupRequestDto requestDto = new MemberSignupRequestDto("김", "r", "email...", "1234", "12345", "010", LocalDate.of(2028, 1, 1));

        this.mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("status").value(GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getHttpStatus().name()))
                .andExpect(jsonPath("code").value(GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getCode()))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isNotEmpty())
                .andExpect(jsonPath("timestamp").exists());
    }

    @ParameterizedTest
    @MethodSource("providedTestDataForSignup")
    @DisplayName("회원가입 통합 테스트 - 실패(유효하지 않은 회원가입 폼)")
    void member_signup_integration_test_fail_caused_by_invalid_signup_form(String name, String nickname, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthday) throws Exception {
        MemberSignupRequestDto requestDto = new MemberSignupRequestDto(name, nickname, email, password, confirmPassword, phoneNumber, birthday);

        this.mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("status").value(GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getHttpStatus().name()))
                .andExpect(jsonPath("code").value(GlobalExceptionCode.INVALID_REQUEST_PARAMETER.getCode()))
                .andExpect(jsonPath("timestamp").exists());
    }

    @Test
    @DisplayName("회원가입 통합 테스트 - 성공")
    void member_signup_integration_test_success() throws Exception {
        MemberSignupRequestDto requestDto = new MemberSignupRequestDto("김새싹", "saessag", "saessag@email.com", "1q2w3e4r5t!", "1q2w3e4r5t!", "010-1234-1234", LocalDate.of(1999, 1, 1));
        this.mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("message").exists());
    }

    private static Stream<Arguments> providedTestDataForSignup() {
        return Stream.of(
                Arguments.of("양성빈", "tester", "email@email.com", "1q2w3e4r5t!", "1q2w3e4r5t!", "010-1234-1234", LocalDate.of(1999, 1, 1)),
                Arguments.of("양성빈", "robert", "test@email.com", "1q2w3e4r5t!", "1q2w3e4r5t!", "010-1234-1234", LocalDate.of(1999, 1, 1)),
                Arguments.of("양성빈", "robert", "email@email.com", "1q2w3e4r5t!", "t5r4e3w2q1@", "010-1234-1234", LocalDate.of(1999, 1, 1)),
                Arguments.of("양성빈", "robert", "email@email.com", "1q2w3e4r5t!", "1q2w3e4r5t!", "010-1111-1111", LocalDate.of(1999, 1, 1))
        );
    }
}