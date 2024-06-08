package com.mogakco.domain.member.service;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.model.request.MemberLoginRequestDto;
import com.mogakco.domain.member.model.request.MemberSignupRequestDto;
import com.mogakco.domain.member.repository.MemberRepository;
import com.mogakco.global.exception.custom.BusinessException;
import com.mogakco.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mogakco.global.util.cookie.CookieUtil.deleteCookie;
import static com.mogakco.global.util.cookie.CookieUtil.setCookie;
import static com.mogakco.global.util.jwt.JwtTokenProvider.*;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

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
     * 로그인 로직
     * @param requestDto
     * @param response
     */
    public void login(MemberLoginRequestDto requestDto, HttpServletResponse response) {
        Member member = this.memberRepository.findByEmail(requestDto.email()).orElseThrow(() -> new BusinessException("이메일 혹은 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new BusinessException("이메일 혹은 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = this.jwtTokenProvider.createToken(member.getId(), ACCESS_TOKEN_EXPIRED_TIME);
        String refreshToken = this.jwtTokenProvider.createToken(member.getId(), REFRESH_TOKEN_EXPIRED_TIME);

        setCookie(ACCESS_TOKEN_INITIAL, SET_COOKIE, accessToken, ACCESS_TOKEN_EXPIRED_TIME, response);
        setCookie(REFRESH_TOKEN_INITIAL, SET_COOKIE, refreshToken, REFRESH_TOKEN_EXPIRED_TIME, response);
    }

    /**
     * 로그아웃 비즈니스 로직
     * @param response
     */
    public void logout(HttpServletResponse response) {
        deleteCookie(ACCESS_TOKEN_INITIAL, response);
        deleteCookie(REFRESH_TOKEN_INITIAL, response);

        SecurityContextHolder.clearContext();
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
