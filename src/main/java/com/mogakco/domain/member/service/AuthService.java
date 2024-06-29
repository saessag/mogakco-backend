package com.mogakco.domain.member.service;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.model.request.*;
import com.mogakco.domain.member.model.response.MemberFindEmailResponseDto;
import com.mogakco.domain.member.repository.MemberRepository;
import com.mogakco.global.exception.custom.BusinessException;
import com.mogakco.global.util.jwt.JwtTokenProvider;
import com.mogakco.global.util.mail.model.EmailMessage;
import com.mogakco.global.util.mail.service.EmailService;
import com.mogakco.global.util.random.RandomCertificationNumberGenerator;
import com.mogakco.global.util.redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

import static com.mogakco.global.util.cookie.CookieUtil.deleteCookie;
import static com.mogakco.global.util.cookie.CookieUtil.setCookie;
import static com.mogakco.global.util.jwt.JwtTokenProvider.*;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RandomCertificationNumberGenerator randomCertificationNumberGenerator;

    private final RedisUtil redisUtil;

    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    @Value("${app.host.server}")
    private String serverHost;

    @Value("${app.host.client}")
    private String clientHost;

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
     * 이메일 찾기 기능 비즈니스 로직
     * @param requestDto
     * @return
     */
    public MemberFindEmailResponseDto findEmail(MemberFindEmailRequestDto requestDto) {
        return this.memberRepository.findEmailByPhoneNumber(requestDto.phoneNumber()).orElseThrow(() -> new BusinessException("입력된 휴대전화번호는 등록되지 않은 번호입니다."));
    }

    /**
     * 이메일을 통한 본인 인증 및 이메일 전송 로직
     * @param requestDto
     */
    public void verifyCredentials(MemberVerifyCredentialsRequestDto requestDto) {
        verifyMemberEmail(requestDto);

        CompletableFuture.runAsync(() -> sendVerificationEmailAsync(requestDto));
    }

    /**
     * 이메일 링크 확인 비즈니스 로직
     * @param requestDto
     * @param response
     */
    public void verifyEmailLink(MemberVerifyEmailLinkRequestDto requestDto, HttpServletResponse response) {
        if (!this.memberRepository.existsByEmail(requestDto.email()) ||
                !this.redisUtil.getData(requestDto.email()).equals(requestDto.certificationNumber())) {
            throw new BusinessException("유효하지 않는 링크입니다.\n이메일 링크 전송을 다시해주세요.");
        }

        redirectToUrl(clientHost + "/auth/update-password?certificationNumber=" + this.redisUtil.getData(requestDto.email()) + "&email=" + requestDto.email(), response);
    }

    @Transactional
    public void updatePassword(MemberUpdatePasswordRequestDto requestDto) {
        Member member = this.memberRepository.findByEmail(requestDto.email()).orElseThrow(() -> new BusinessException("유효하지 않은 링크입니다. url을 다시 확인해주세요."));

        if (!this.redisUtil.getData(requestDto.email()).equals(requestDto.certificationNumber())) {
            throw new BusinessException("유효하지 않은 링크입니다. url을 다시 확인해주세요.");
        }

        requestDto.validatePasswordAndConfirmPassword();

        if (passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new BusinessException("기존에 등록한 비밀번호와 일치합니다.");
        }

        member.updatePassword(this.passwordEncoder.encode(requestDto.password()));
    }

    /**
     * url redirect
     * @param url
     * @param response
     */
    private void redirectToUrl(String url, HttpServletResponse response) {
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            log.error("redirect url failed ", e);
        }
    }

    /**
     * 인증코드 & 이메일 폼 생성 및 이메일 전송 비동기 로직
     * @param requestDto
     */
    @Async(value = "mailExecutor")
    public void sendVerificationEmailAsync(MemberVerifyCredentialsRequestDto requestDto) {
        String certificationNumber = generateAndSetCertificationNumber(requestDto);
        EmailMessage emailMessage = prepareEmailMessage(requestDto, certificationNumber);

        sendVerificationEmail(emailMessage);
    }

    /**
     * 이메일 전송 로직
     * @param emailMessage
     */
    private void sendVerificationEmail(EmailMessage emailMessage) {
        emailService.sendEmail(emailMessage);
    }

    /**
     * 이메일 폼 생성 로직
     * @param requestDto
     * @param certificationNumber
     * @return
     */
    private EmailMessage prepareEmailMessage(MemberVerifyCredentialsRequestDto requestDto, String certificationNumber) {
        Context context = new Context();

        context.setVariable("link", "/api/auth/verify-email-link?certificationNumber=" + certificationNumber + "&email=" + requestDto.email());
        context.setVariable("title", "비밀번호 변경링크입니다.");
        context.setVariable("message", "비밀번호 변경버튼을 눌러 비밀번호를 변경해주세요.");
        context.setVariable("host", serverHost);
        context.setVariable("year", LocalDate.now(ZoneId.of("Asia/Seoul")).getYear());

        String message = this.templateEngine.process("mail/verify-email", context);

        return EmailMessage.builder()
                .to(requestDto.email())
                .subject("[MOGAKCO] 비밀번호 변경 링크입니다.")
                .message(message)
                .build();
    }

    /**
     * 인증코드 생성 로직
     * @param requestDto
     * @return
     */
    private String generateAndSetCertificationNumber(MemberVerifyCredentialsRequestDto requestDto) {
        String certificationNumber = this.randomCertificationNumberGenerator.getCertificationNumber();
        this.redisUtil.setData(requestDto.email(), certificationNumber, 5L);

        return certificationNumber;
    }

    /**
     * 이메일 유효성 검사
     * @param requestDto
     */
    private void verifyMemberEmail(MemberVerifyCredentialsRequestDto requestDto) {
        if (!this.memberRepository.existsByEmail(requestDto.email())) {
            throw new BusinessException("등록되지 않은 이메일입니다.\n이메일 찾기를 통하여 이메일을 확인해주세요.");
        }
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
