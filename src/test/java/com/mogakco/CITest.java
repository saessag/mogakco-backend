package com.mogakco;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CITest {
    @Test
    public void test() {
        assertThat(1 + 1).isEqualTo(2);
    }

    @Test
    public void failingTest() {
        assertThat(1 + 1).isEqualTo(1);
    }
}
