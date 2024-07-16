package epams.framework.storage;

/**
 * @author K140024
 * @implNote 파일 저장소와 관련된 예외를 나타내는 런타임 예외 클래스
 * @since 2024-06-11
 */
public class StorageException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -9000677512538691861L;

    /**
     * 메시지로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     */
    public StorageException(final String message) {
        super(message);
    }

    /**
     * 메시지와 원인으로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     * @param cause 예외의 원인
     */
    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
