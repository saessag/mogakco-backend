package com.mogakco.global.config.security;

import com.mogakco.global.util.jwt.JwtTokenProvider;
import com.mogakco.global.util.jwt.entry.CustomAuthenticationEntryPoint;
import com.mogakco.global.util.jwt.filter.CustomJwtAuthenticationFilter;
import com.mogakco.global.util.jwt.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${cors.origin}")
    private String corsOrigin;

    private final Environment environment;

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public static final List<String> WHITE_LIST = Arrays.asList("/api/auth/signup", "/api/auth/login", "/api/auth/find-email", "/api/auth/verify-credentials", "/api/auth/verify-email-link", "/api/auth/update-password");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    WHITE_LIST.forEach(url -> auth.requestMatchers(url).permitAll());

                    auth.requestMatchers("/docs/*", "/actuator/*").hasRole("ADMIN"); // rest docs + actuator 부분 관리자만 접근 허용

                    if (!Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
                        auth.requestMatchers(PathRequest.toH2Console()).permitAll(); // h2 console prod profile 외에 허용
                    }

                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();

                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(new CustomJwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern(corsOrigin); // FE domain 허용
        corsConfiguration.setAllowedMethods(Arrays.asList(GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name())); // Http Method 허용
        corsConfiguration.setAllowedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, CACHE_CONTROL, SET_COOKIE)); // 특정 Http Headers 허용
        corsConfiguration.setAllowCredentials(true); // credential 허용

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
