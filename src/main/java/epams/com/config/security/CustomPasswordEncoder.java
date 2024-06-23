package epams.com.config.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.core.codec.EncodingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 사용자 비밀번호를 커스텀 인코딩 방식으로 처리하는 클래스
 * @since 2024-06-11
 */
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

    /**
     * @author K140024
     * @implNote 기본 BCryptPasswordEncoder 인스턴스
     * @since 2024-06-11
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * @author K140024
     * @implNote 비밀번호를 SHA-256 방식으로 인코딩하는 메서드
     * @since 2024-06-11
     */
    @Override
    public String encode(final CharSequence rawPassword) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final Encoder encoder = Base64.getEncoder();
            final byte[] digest = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(digest);
        } catch (Exception e) {
            log.error("Encoding failed", e);
            throw new EncodingException("Failed to encode SHA-256");
        }
    }

    /**
     * @author K140024
     * @implNote 인코딩된 비밀번호와 원본 비밀번호가 일치하는지 확인하는 메서드
     * @since 2024-06-11
     */
    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        // 2024-06-22 취약점 조치 (CWE-256 Unprotected Storage of Credentials)
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
