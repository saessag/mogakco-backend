package com.mogakco.global.util.jwt.filter;

import com.mogakco.global.util.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.mogakco.global.config.security.SecurityConfig.WHITE_LIST;
import static com.mogakco.global.util.cookie.CookieUtil.setCookie;
import static com.mogakco.global.util.jwt.JwtTokenProvider.*;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (WHITE_LIST.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);

            return;
        }

        Cookie[] cookies = request.getCookies();

        String accessToken = Optional.ofNullable(cookies)
                .flatMap(someCookie -> Arrays.stream(someCookie)
                        .filter(cookie -> ACCESS_TOKEN_INITIAL.equals(cookie.getName()))
                        .findFirst().map(Cookie::getValue)
                ).orElse(null);

        if (StringUtils.hasText(accessToken) && this.jwtTokenProvider.validateToken(accessToken)) {
            Authentication authentication = this.jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (!request.getRequestURI().equals("/api/auth/logout")) {
                setCookie(ACCESS_TOKEN_INITIAL, SET_COOKIE, accessToken, ACCESS_TOKEN_EXPIRED_TIME, response);
            }
        } else {
            String refreshToken = Optional.ofNullable(cookies)
                    .flatMap(someCookie -> Arrays.stream(someCookie)
                            .filter(cookie -> REFRESH_TOKEN_INITIAL.equals(cookie.getName()))
                            .findFirst().map(Cookie::getValue)
                    ).orElse(null);

            if (StringUtils.hasText(refreshToken) && this.jwtTokenProvider.validateToken(refreshToken)) {
                reissueToken(refreshToken, request, response);
            } else {
                log.info("토큰이 만료되었습니다.");
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 토큰 갱신 로직
     * @param token
     * @param request
     * @param response
     */
    private void reissueToken(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)) {
                long pk = Long.parseLong(this.jwtTokenProvider.getMemberPK(token));
                String renewAccessToken = this.jwtTokenProvider.createToken(pk, ACCESS_TOKEN_EXPIRED_TIME);
                String renewRefreshToken = this.jwtTokenProvider.createToken(pk, REFRESH_TOKEN_EXPIRED_TIME);

                if (!request.getRequestURI().equals("/api/auth/logout")) {
                    setCookie(ACCESS_TOKEN_INITIAL, SET_COOKIE, renewAccessToken, ACCESS_TOKEN_EXPIRED_TIME, response);
                    setCookie(REFRESH_TOKEN_INITIAL, SET_COOKIE, renewRefreshToken, REFRESH_TOKEN_EXPIRED_TIME, response);
                }

                Authentication authentication = this.jwtTokenProvider.getAuthentication(renewAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("renew token error", e);
            SecurityContextHolder.clearContext();
        }
    }
}
