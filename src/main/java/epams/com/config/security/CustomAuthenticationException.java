package epams.com.config.security;

public class CustomAuthenticationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3078530608175015209L;

	public CustomAuthenticationException(String message) {
        super(message);
    }
}