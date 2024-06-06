package com.mogakco.global.util.jwt;

import com.mogakco.domain.member.service.MemberDetailService;
import com.mogakco.global.util.encryption.Aes256EncryptionUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.JwsHeader.ALGORITHM;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String salt;

    private Key secretKey;

    private final MemberDetailService memberDetailService;

    private final Aes256EncryptionUtil aes256EncryptionUtil;

    public static final String ACCESS_TOKEN_INITIAL = "AT_";

    public static final long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 2;

    public static final String REFRESH_TOKEN_INITIAL = "RT_";

    public static final long REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 * 14;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(this.salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long id, long expiredTime) {
        Claims claims = Jwts.claims().setSubject(aes256EncryptionUtil.encrypt(String.valueOf(id)));
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setHeaderParam(ALGORITHM, HS256)
                .setIssuer("mogakco.com")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(this.secretKey, HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = this.memberDetailService.loadUserByUsername(getMemberPK(accessToken));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("jwt token expired : {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.error("jwt token is not validated : {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("validated token error occurred : {}", e.getMessage());
            return false;
        }
    }

    private String getMemberPK(String token) {
        return this.aes256EncryptionUtil.decrypt(Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody().getSubject());
    }
}
