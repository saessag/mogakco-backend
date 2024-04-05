package com.mogakco.global.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogakco.global.annotation.IntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@IntegrationTest
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
