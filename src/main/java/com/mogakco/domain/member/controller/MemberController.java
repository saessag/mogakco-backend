package com.mogakco.domain.member.controller;

import com.mogakco.domain.member.dto.request.MemberCreateRequest;
import com.mogakco.domain.member.dto.response.MemberCreateResponse;
import com.mogakco.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/users/sign-up")
    public ResponseEntity<MemberCreateResponse> createUser(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.createUser(memberCreateRequest));
    }
}
