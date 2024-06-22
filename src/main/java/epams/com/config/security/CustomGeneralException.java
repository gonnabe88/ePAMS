package epams.com.config.security;

public class CustomGeneralException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3078530608175015209L;

	public CustomGeneralException(String message) {
        super(message);
    }
	
    public CustomGeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}