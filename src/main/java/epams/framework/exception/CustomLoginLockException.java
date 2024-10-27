package epams.framework.exception;

/**
 * @author K140024
 * @implNote 일반적인 런타임 예외를 처리하기 위한 커스텀 예외 클래스
 * @since 2024-06-11
 */
public class CustomLoginLockException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 메시지로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     */
    public CustomLoginLockException(final String message) {
        super(message);
    }

    /**
     * 메시지와 원인으로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     * @param cause 예외의 원인
     */
    public CustomLoginLockException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
