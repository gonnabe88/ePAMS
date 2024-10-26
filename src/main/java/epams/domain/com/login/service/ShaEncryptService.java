package epams.domain.com.login.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.net.URLDecoder;

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
     * @implNote 주어진 base64 인코딩 텍스트를 SHA-256으로 암호화하는 메서드
     * @since 2024-06-11
     */
    public String encrypt(final String encodedText) throws CustomGeneralException {
        try {
            // 디코딩(base64)
            final Decoder decoder = Base64.getDecoder();
            final byte[] decodedBytes = decoder.decode(encodedText);
            final String base64DecodeStr = new String(decodedBytes, StandardCharsets.UTF_8);
            // 디코딩(URL)
            final String decodedStr = URLDecoder.decode(base64DecodeStr, StandardCharsets.UTF_8);
            // SHA-256 암호화
            final MessageDigest msg = MessageDigest.getInstance("SHA-256");
            msg.update("".getBytes(StandardCharsets.UTF_8)); // 빈 솔트값 추가 (취약점)
            final Encoder encoder = Base64.getEncoder();
            final byte[] digest = msg.digest(decodedStr.getBytes(StandardCharsets.UTF_8));
            // 인코딩
            return encoder.encodeToString(digest);
        } catch (final NoSuchAlgorithmException e) {
            throw new CustomGeneralException("Fail to encrypt SHA-256", e);
        }
    }

    /**
     * @author K140024
     * @implNote 암호화된 값과 평문 텍스트가 동일한지 비교하는 메서드
     * @since 2024-08-11
     */
    public boolean match(final String plainText, final String encryptedText) throws CustomGeneralException {
        // Base64 encode the plain text
        String encodedPlainText = Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));

        // Encrypt the Base64 encoded plain text
        String encryptedPlainText = encrypt(encodedPlainText);

        // Compare the newly encrypted text with the provided encrypted text
        return encryptedPlainText.equals(encryptedText);
    }
}
