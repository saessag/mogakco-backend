package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.domain.member.service.AuthService;
import com.mogakco.global.response.success.SuccessCommonApiResponse;
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
}
