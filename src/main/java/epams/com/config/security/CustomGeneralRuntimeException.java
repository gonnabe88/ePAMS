package epams.com.config.security;

public class CustomGeneralRuntimeException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3078530608175015209L;

	public CustomGeneralRuntimeException(String message) {
        super(message);
    }
	
    public CustomGeneralRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}