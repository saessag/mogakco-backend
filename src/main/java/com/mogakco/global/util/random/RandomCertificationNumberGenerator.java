package com.mogakco.global.util.random;

import com.mogakco.global.exception.custom.BusinessException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class RandomCertificationNumberGenerator implements CertificationNumberGenerator {

    @Override
    public String getCertificationNumber() {
        try {
            int number = SecureRandom.getInstanceStrong().nextInt(900000) + 100000;

            return String.valueOf(number);
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("인증코드 생성에 실패하였습니다.");
        }
    }
}
