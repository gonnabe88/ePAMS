package epams.com.config.security;

/**
 * @author K140024
 * @implNote General 예외처리 커스텀 (RuntimeException 클래스 상속)
 * @since 2024-06-11
 */
public class CustomGeneralException extends RuntimeException {
    
	private static final long serialVersionUID = -3078530608175015209L;

	/**
	 * @author K140024
	 * @implNote General 예외처리 메소드
	 * @param String
	 * @since 2024-06-11
	 */
	public CustomGeneralException(final String message) {
        super(message);
    }
	
	/**
	 * @author K140024
	 * @implNote General 예외처리 메소드
	 * @param String, Throwable
	 * @since 2024-06-11
	 */
    public CustomGeneralException(final String message, final Throwable cause) {
        super(message, cause);
    }
}