package com.mogakco.global.config;

import com.mogakco.global.util.TestDataUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestDataConfig {

    private final TestDataUtil testDataUtil;

    public TestDataConfig(TestDataUtil testDataUtil) {
        this.testDataUtil = testDataUtil;
    }

    @PostConstruct
    public void init() {
        this.testDataUtil.createTestMember();
    }
}
