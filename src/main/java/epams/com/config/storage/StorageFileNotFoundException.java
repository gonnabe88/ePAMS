package epams.com.config.storage;

/**
 * @author K140024
 * @implNote 파일 저장소에서 파일을 찾지 못했을 때 발생하는 예외 클래스
 * @since 2024-06-11
 */
public class StorageFileNotFoundException extends StorageException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 메시지로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     */
    public StorageFileNotFoundException(final String message) {
        super(message);
    }

    /**
     * 메시지와 원인으로 초기화하는 생성자
     * 
     * @param message 예외 메시지
     * @param cause 예외의 원인
     */
    public StorageFileNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
