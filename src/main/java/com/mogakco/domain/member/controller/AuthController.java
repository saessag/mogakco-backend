package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.model.request.MemberFindEmailRequestDto;
import com.mogakco.domain.member.model.request.MemberLoginRequestDto;
import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.domain.member.model.response.MemberFindEmailResponseDto;
import com.mogakco.domain.member.service.AuthService;
import com.mogakco.global.response.success.SuccessCommonApiResponse;
import com.mogakco.global.response.success.SuccessSingleApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
