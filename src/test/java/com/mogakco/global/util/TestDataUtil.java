package com.mogakco.global.util;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.repository.MemberRepository;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@TestComponent
public class TestDataUtil {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    public TestDataUtil(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createTestMember() {
        Member member = Member.builder()
                .name("테스터")
                .nickname("tester")
                .email("test@email.com")
                .password(passwordEncoder.encode("1q2w3e4r5t!"))
                .phoneNumber("010-1111-1111")
                .birthday(LocalDate.of(1999, 1, 1))
                .build();

        this.memberRepository.save(member);
    }
}
