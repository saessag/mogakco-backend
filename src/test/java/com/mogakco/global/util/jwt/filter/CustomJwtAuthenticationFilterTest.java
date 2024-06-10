package com.mogakco.global.util.jwt.filter;

import com.mogakco.domain.member.service.MemberDetailService;
import com.mogakco.global.controller.BaseControllerTest;
import com.mogakco.global.util.jwt.JwtTokenProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;

import static com.mogakco.global.util.jwt.JwtTokenProvider.*;
import static com.mogakco.global.util.jwt.JwtTokenProvider.REFRESH_TOKEN_EXPIRED_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CustomJwtAuthenticationFilterTest extends BaseControllerTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private MemberDetailService memberDetailService;

    @Test
    @DisplayName("토큰 갱신 통합 테스트 - 성공")
    void renew_token_integration_test_success() throws Exception {
        String expiredAccessToken = "expiredAT";
        String validRefreshToken = "validRT";
        String userId = "12345"; // 예제 사용자 ID

        // secretKey 목킹
        Key mockedKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", mockedKey);

        // 다른 필요한 목 설정
        UserDetails mockUserDetails = new User("user", "password", new ArrayList<>());
        when(memberDetailService.loadUserByUsername("12345")).thenReturn(mockUserDetails);
        when(jwtTokenProvider.getMemberPK(anyString())).thenReturn(userId);

        // 토큰 유효성 검사 Mock 설정
        when(jwtTokenProvider.validateToken(expiredAccessToken)).thenReturn(false);
        when(jwtTokenProvider.validateToken(validRefreshToken)).thenReturn(true);

        // 새 토큰 생성 Mock 설정
        when(jwtTokenProvider.createToken(anyLong(), eq(JwtTokenProvider.ACCESS_TOKEN_EXPIRED_TIME)))
                .thenReturn("newAccessToken");
        when(jwtTokenProvider.createToken(anyLong(), eq(JwtTokenProvider.REFRESH_TOKEN_EXPIRED_TIME)))
                .thenReturn("newRefreshToken");

        // Authentication 객체 Mock 설정
        when(jwtTokenProvider.getAuthentication("newAccessToken"))
                .thenReturn(new UsernamePasswordAuthenticationToken(mockUserDetails, "", Collections.emptyList()));

        // 갱신 로직을 트리거하는 HTTP 요청 시뮬레이션
        mockMvc.perform(post("/api/auth/logout")
                        .cookie(new Cookie(ACCESS_TOKEN_INITIAL, expiredAccessToken),
                                new Cookie(REFRESH_TOKEN_INITIAL, validRefreshToken)))
                .andExpect(status().isOk()) // 상태 코드 검증
                .andExpect(cookie().exists(ACCESS_TOKEN_INITIAL))
                .andExpect(cookie().exists(REFRESH_TOKEN_INITIAL));

        // 메서드 호출 검증
        verify(jwtTokenProvider, times(1)).validateToken(expiredAccessToken);
        verify(jwtTokenProvider, times(2)).validateToken(validRefreshToken);
        verify(jwtTokenProvider, times(1)).createToken(anyLong(), eq(ACCESS_TOKEN_EXPIRED_TIME));
        verify(jwtTokenProvider, times(1)).createToken(anyLong(), eq(REFRESH_TOKEN_EXPIRED_TIME));
    }
}