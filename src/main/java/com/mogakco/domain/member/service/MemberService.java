package com.mogakco.domain.member.service;

import com.mogakco.domain.member.dto.request.MemberCreateRequest;
import com.mogakco.domain.member.dto.response.MemberCreateResponse;
import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.repository.MemberRepository;
import com.mogakco.global.exception.CustomException;
import com.mogakco.global.response.error.ErrorResponse;
import com.mogakco.global.response.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberCreateResponse createUser(MemberCreateRequest memberCreateRequest) {
        if (memberRepository.validateDuplicationNickname(memberCreateRequest.getNickname())) {
            throw new CustomException(ErrorResponse.DUPLICATE_USER_NICKNAME);
        }

        Member createdMember = Member.builder()
                .name(memberCreateRequest.getName())
                .nickname(memberCreateRequest.getNickname())
                .email(memberCreateRequest.getEmail())
                .password(passwordEncoder.encode(memberCreateRequest.getPassword()))
                .build();
        memberRepository.save(createdMember);

        return MemberCreateResponse.builder()
                .name(createdMember.getName())
                .nickname(createdMember.getNickname())
                .email(createdMember.getEmail())
                .responseCode(SuccessResponse.SUCCESS_CREATE_USER.getCode())
                .build();
    }
}
