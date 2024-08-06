package epams.domain.com.login.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;

import epams.framework.exception.CustomGeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote SHA-256 암호화를 처리하는 서비스 클래스
 * @since 2024-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShaEncryptService {

    /**
     * @author K140024
     * @implNote 주어진 평문 텍스트를 SHA-256으로 암호화하는 메서드
     * @since 2024-06-11
     */
    public String encrypt(final String planText) throws CustomGeneralException {
        try {
            final Decoder decoder = Base64.getDecoder();
            final byte[] decodedBytes = decoder.decode(planText);
            final String decodedStr = new String(decodedBytes, StandardCharsets.UTF_8);
            log.warn(decodedStr);

            final MessageDigest msg = MessageDigest.getInstance("SHA-256");
            final Encoder encoder = Base64.getEncoder();
            final byte[] digest = msg.digest(decodedStr.getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(digest);
        } catch (final NoSuchAlgorithmException e) {
            throw new CustomGeneralException("Fail to encrypt SHA-256", e);
        }
    }
}
