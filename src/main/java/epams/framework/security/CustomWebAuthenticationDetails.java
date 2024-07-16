package epams.framework.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote CustomWebAuthenticationDetails 인증 정보 추가(커스텀)
 * @since 2024-06-11
 */
@Slf4j
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	
	private static final long serialVersionUID = 8476298563946165595L;
	
	/**
	 * @author K140024
	 * @implNote 인증 시 사용될 otp 번호
	 * @since 2024-06-11
	 */
	private final String OTP;
	
	/**
	 * @author K140024
	 * @implNote 인증 시 사용될 Multi-Fator 인증 방식
	 * @since 2024-06-11
	 */
	private final String MFA;
	
	/**
	 * Records the remote address and will also set the session Id if a session already
	 * exists (it won't create one).
	 *
	 * @param request that the authentication request was received from
	 */
	public CustomWebAuthenticationDetails(final HttpServletRequest request) {
		super(request);
		OTP = request.getParameter("OTP");
		MFA = request.getParameter("MFA");
	}
	
	/**
	 * @author K140024
	 * @implNote otp getter
	 * @since 2024-06-11
	 */
	public String getOTP() {
		return OTP;
	}
	
	/**
	 * @author K140024
	 * @implNote mfa getter
	 * @since 2024-06-11
	 */
	public String getMFA() {
		return MFA;
	}
}