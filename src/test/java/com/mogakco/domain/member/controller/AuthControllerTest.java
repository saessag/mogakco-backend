package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.model.request.MemberFindEmailRequestDto;
import com.mogakco.domain.member.model.request.MemberLoginRequestDto;
import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.domain.member.model.request.MemberVerifyCredentialsRequestDto;
import com.mogakco.global.controller.BaseControllerTest;
import com.mogakco.global.exception.GlobalExceptionCode;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.mogakco.global.util.TestAuthUtil.performLoginAndGetCookies;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @DisplayName("로그인 통합 테스트 - 실패(잘못된 입력값)")
    void member_login_integration_test_fail_caused_by_wrong_input() throws Exception {
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("email...", "1234");

        this.mockMvc.perform(post("/api/auth/login")
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

    @Test
    @DisplayName("로그인 통합 테스트 - 실패(이메일 불일치)")
    void member_login_integration_test_fail_caused_by_not_correct_email() throws Exception {
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("email@email.com", "1q2w3e4r5t!");

        this.mockMvc.perform(post("/api/auth/login")
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
    @DisplayName("로그인 통합 테스트 - 실패(비밀번호 불일치)")
    void member_login_integration_test_fail_caused_by_not_correct_password() throws Exception {
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test@email.com", "t5r4e3w2q1!");

        this.mockMvc.perform(post("/api/auth/login")
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
    @DisplayName("로그인 통합 테스트 - 성공")
    void member_login_integration_test_success() throws Exception {
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test@email.com", "1q2w3e4r5t!");

        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").exists())
                .andExpect(header().exists(SET_COOKIE))
                .andExpect(header().stringValues(SET_COOKIE, hasItem(startsWith("AT="))))
                .andExpect(header().stringValues(SET_COOKIE, hasItem(startsWith("RT="))));
    }

    @Test
    @DisplayName("로그아웃 통합 테스트 - 실패(인증되지 않은 사용자)")
    void member_logout_integration_test_fail_caused_by_unauthenticated_member() throws Exception {
        this.mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 통합 테스트 - 성공")
    void member_logout_integration_test_success() throws Exception {
        Cookie[] cookies = performLoginAndGetCookies(this.mockMvc, this.objectMapper);

        this.mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .cookie(cookies))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(header().exists(SET_COOKIE))
                .andExpect(header().stringValues(SET_COOKIE, hasItem(startsWith("AT=;"))))
                .andExpect(header().stringValues(SET_COOKIE, hasItem(startsWith("RT=;"))))
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @DisplayName("이메일 찾기 통합 테스트 - 실패(잘못된 입력값)")
    void find_email_integration_test_fail_caused_by_wrong_input() throws Exception {
        MemberFindEmailRequestDto requestDto = new MemberFindEmailRequestDto("010");

        this.mockMvc.perform(post("/api/auth/find-email")
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

    @Test
    @DisplayName("이메일 찾기 통합 테스트 - 실패(잘못된 휴대전화번호)")
    void find_email_integration_test_fail_caused_by_not_correct_phone_number() throws Exception {
        MemberFindEmailRequestDto requestDto = new MemberFindEmailRequestDto("010-1234-1234");

        this.mockMvc.perform(post("/api/auth/find-email")
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
    @DisplayName("이메일 찾기 통합 테스트 - 성공")
    void find_email_integration_test_success() throws Exception {
        MemberFindEmailRequestDto requestDto = new MemberFindEmailRequestDto("010-1111-1111");

        this.mockMvc.perform(post("/api/auth/find-email")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data.email").exists());
    }

    @Test
    @DisplayName("비밀번호 찾기(회원 인증) 통합 테스트 - 실패(잘못된 입력 값)")
    void member_verify_credentials_integration_test_fail_caused_by_wrong_input() throws Exception {
        MemberVerifyCredentialsRequestDto requestDto = new MemberVerifyCredentialsRequestDto("email...");

        this.mockMvc.perform(post("/api/auth/verify-credentials")
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

    @Test
    @DisplayName("비밀번호 찾기(회원 인증) 통합 테스트 - 실패(잘못된 이메일)")
    void member_verify_credentials_integration_test_fail_caused_by_wrong_email() throws Exception {
        MemberVerifyCredentialsRequestDto requestDto = new MemberVerifyCredentialsRequestDto("email@email.com");

        this.mockMvc.perform(post("/api/auth/verify-credentials")
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
    @DisplayName("비밀번호 찾기(회원 인증) 통합 테스트 - 성공")
    void member_verify_credentials_integration_test_success() throws Exception {
        MemberVerifyCredentialsRequestDto requestDto = new MemberVerifyCredentialsRequestDto("test@email.com");

        this.mockMvc.perform(post("/api/auth/verify-credentials")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .accept(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(this.objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
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