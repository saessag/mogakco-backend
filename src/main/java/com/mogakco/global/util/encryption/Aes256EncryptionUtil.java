package com.mogakco.global.util.encryption;

import com.mogakco.global.exception.custom.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
@Component
public class Aes256EncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    @Value("${AES_KEY}")
    private String aesKey;  // 이제 여기에 환경 변수 값을 주입받습니다.

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("encrypt method occurred error : {}", e.getMessage());
            throw new BusinessException("암호화에 실패하였습니다.");
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, generateKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error("decrypt method occurred error : {}", e.getMessage());
            throw new BusinessException("복호화에 실패하였습니다.");
        }
    }

    private Key generateKey() {
        byte[] key = aesKey.getBytes();
        return new SecretKeySpec(key, ALGORITHM);
    }
}