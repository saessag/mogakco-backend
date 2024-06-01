package com.mogakco.global.util;

import com.mogakco.domain.member.entity.Member;
import com.mogakco.domain.member.repository.MemberRepository;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDate;

@TestComponent
public class TestDataUtil {

    private final MemberRepository memberRepository;


    public TestDataUtil(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createTestMember() {
        Member member = Member.builder()
                .name("테스터")
                .nickname("tester")
                .email("test@email.com")
                .password("1q2w3e4r5t!")
                .phoneNumber("010-1111-1111")
                .birthday(LocalDate.of(1999, 1, 1))
                .build();

        this.memberRepository.save(member);
    }
}
