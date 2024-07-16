package epams.domain.com.login.util.webauthn.utility;

import java.security.SecureRandom;

import com.yubico.webauthn.data.ByteArray;

import lombok.NoArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * @author K140024
 * @implNote Webauthn 관련 유틸리티 클래스를 제공하는 서비스
 * @since 2024-06-11
 */
@NoArgsConstructor
@Service
public class Utility {

    /**
     * @author K140024
     * @implNote 암호화 강도를 위한 SecureRandom 인스턴스
     * @since 2024-06-11
     */
    private static final SecureRandom random = new SecureRandom();


    /**
     * @author K140024
     * @implNote 지정된 길이의 임의의 바이트 배열을 생성하는 메소드
     * @since 2024-06-11
     * @param length 생성할 바이트 배열의 길이
     * @return 생성된 임의의 바이트 배열을 포함하는 ByteArray 객체
     */
    public ByteArray generateRandom(final int length) {
        final byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return new ByteArray(bytes);
    }
}
