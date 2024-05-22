package com.mogakco.domain.member.service;

import com.mogakco.domain.member.dto.request.MemberCreateRequest;
import com.mogakco.domain.member.dto.response.MemberCreateResponse;
import com.mogakco.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void 회원가입_테스트() throws Exception {
        // Given
        MemberCreateRequest request = new MemberCreateRequest();
        request.setName("wonjun");
        request.setNickname("wonjun1234");
        request.setEmail("wonjun123@gmail.com");
        request.setPassword("abc1234!@#");

        // When
        when(memberRepository.validateDuplicationNickname("wonjun1234")).thenReturn(false);
        when(passwordEncoder.encode("abc1234!@#")).thenReturn("encodedPassword");
        MemberCreateResponse createdUser = memberService.createUser(request);

        // Then
        Assertions.assertEquals("wonjun", createdUser.getName());
        Assertions.assertEquals("wonjun", createdUser.getName());
        Assertions.assertEquals("wonjun", createdUser.getName());
        Assertions.assertEquals("wonjun", createdUser.getName());
    }
}