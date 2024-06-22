package epams.com.config.security;

/**
 * @author K140024
 * @implNote 암호화 예외를 나타내는 클래스
 * @since 2024-06-22
 */
public class CustomGeneralEncryptionException extends Exception {
    private static final long serialVersionUID = 1L;

    public CustomGeneralEncryptionException(String message) {
        super(message);
    }

    public CustomGeneralEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}