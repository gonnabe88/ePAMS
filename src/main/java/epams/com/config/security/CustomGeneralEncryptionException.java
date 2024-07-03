package epams.com.config.security;

/**
 * @author K140024
 * @implNote 암호화 예외를 나타내는 클래스
 * @since 2024-06-22
 */
public class CustomGeneralEncryptionException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 메시지로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     */
    public CustomGeneralEncryptionException(final String message) {
        super(message);
    }

    /**
     * 메시지와 원인으로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     * @param cause 예외의 원인
     */
    public CustomGeneralEncryptionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
