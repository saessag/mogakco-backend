package com.mogakco.domain.member.service;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.domain.member.repository.MemberRepository;
import com.mogakco.global.exception.custom.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 로직
     * @param requestDto
     */
    @Transactional
    public void signup(MemberSignupRequestDto requestDto) {
        validateMemberSignupForm(requestDto);

        Member member = requestDto.toMember(passwordEncoder);

        this.memberRepository.save(member);
    }

    /**
     * 회원가입 입력 폼 검증 로직
     * @param requestDto
     */
    private void validateMemberSignupForm(MemberSignupRequestDto requestDto) {
        if (this.memberRepository.existsByNickname(requestDto.nickname())) {
            throw new BusinessException("이미 등록된 닉네임입니다.");
        }

        if (this.memberRepository.existsByEmail(requestDto.email())) {
            throw new BusinessException("이미 등록된 이메일입니다.");
        }

        if (this.memberRepository.existsByPhoneNumber(requestDto.phoneNumber())) {
            throw new BusinessException("이미 등록된 휴대전화번호입니다.");
        }

        requestDto.validatePasswordAndConfirmPassword();
    }
}
