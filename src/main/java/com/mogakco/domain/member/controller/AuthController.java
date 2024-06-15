package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.model.request.*;
import com.mogakco.domain.member.model.response.MemberFindEmailResponseDto;
import com.mogakco.domain.member.service.AuthService;
import com.mogakco.global.response.success.SuccessCommonApiResponse;
import com.mogakco.global.response.success.SuccessSingleApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessCommonApiResponse> signup(@Valid @RequestBody MemberSignupRequestDto requestDto) {
        this.authService.signup(requestDto);

        URI createdUri = URI.create("/api/member/" + requestDto.nickname());
        SuccessCommonApiResponse response = SuccessCommonApiResponse.of("회원가입이 정상적으로 처리되었습니다.");

        return ResponseEntity.created(createdUri).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessCommonApiResponse> login(@Valid @RequestBody MemberLoginRequestDto requestDto,
                                                          HttpServletResponse response) {
        this.authService.login(requestDto, response);

        SuccessCommonApiResponse apiResponse = SuccessCommonApiResponse.of("로그인이 정상적으로 처리되었습니다.");

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessCommonApiResponse> logout(HttpServletResponse response) {
        this.authService.logout(response);

        SuccessCommonApiResponse apiResponse = SuccessCommonApiResponse.of("로그아웃이 정상적으로 처리되었습니다.");

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/find-email")
    public ResponseEntity<SuccessSingleApiResponse<MemberFindEmailResponseDto>> findEmail(@Valid @RequestBody MemberFindEmailRequestDto requestDto) {
        MemberFindEmailResponseDto responseDto = this.authService.findEmail(requestDto);
        SuccessSingleApiResponse<MemberFindEmailResponseDto> response = SuccessSingleApiResponse.of("이메일 찾기가 정상적으로 처리되었습니다.", responseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-credentials")
    public ResponseEntity<SuccessCommonApiResponse> verifyCredentials(@Valid @RequestBody MemberVerifyCredentialsRequestDto requestDto) {
        this.authService.verifyCredentials(requestDto);

        SuccessCommonApiResponse apiResponse = SuccessCommonApiResponse.of("이메일이 정상적으로 전송되었습니다.");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/verify-email-link")
    public ResponseEntity<SuccessCommonApiResponse> verifyEmailLink(@Valid MemberVerifyEmailLinkRequestDto requestDto, HttpServletResponse response) {
        this.authService.verifyEmailLink(requestDto, response);

        SuccessCommonApiResponse apiResponse = SuccessCommonApiResponse.of("이메 인증이 완료되었습니다.");

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update-password")
    public ResponseEntity<SuccessCommonApiResponse> updatePassword(@Valid @RequestBody MemberUpdatePasswordRequestDto requestDto) {
        this.authService.updatePassword(requestDto);

        SuccessCommonApiResponse apiResponse = SuccessCommonApiResponse.of("비밀번호가 정상적으로 변경되었습니다.");

        return ResponseEntity.ok(apiResponse);
    }
}
