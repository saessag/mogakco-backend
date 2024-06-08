package com.mogakco.global.util.cookie;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtil {

    public static void setCookie(String cookieName, String headerName, String token, long duration, HttpServletResponse response) {
        ResponseCookie httpOnlyCookie = ResponseCookie
                .from(cookieName, token)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofMillis(duration))
                .sameSite("Lax")
                .build();

        response.addHeader(headerName, httpOnlyCookie.toString());
    }
}
