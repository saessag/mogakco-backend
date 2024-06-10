package com.mogakco.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogakco.domain.member.model.request.MemberLoginRequestDto;
import com.mogakco.global.controller.BaseControllerTest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Disabled;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
public class TestAuthUtil extends BaseControllerTest {

    public static Cookie[] performLoginAndGetCookies(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(objectMapper.writeValueAsString(new MemberLoginRequestDto("test@email.com", "1q2w3e4r5t!"))))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("AT"))
                .andExpect(cookie().exists("RT"))
                .andReturn();

        return mvcResult.getResponse().getCookies();
    }
}
