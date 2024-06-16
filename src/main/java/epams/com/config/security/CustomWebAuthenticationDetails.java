package epams.com.config.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 8476298563946165595L;
	private String UUID;
	private String OTP;
	private String MFA;
	
	/**
	 * Records the remote address and will also set the session Id if a session already
	 * exists (it won't create one).
	 *
	 * @param request that the authentication request was received from
	 */
	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		OTP = request.getParameter("OTP");
		MFA = request.getParameter("MFA");		
		log.warn("{} {}",request.getParameter("MFA"), request.getParameter("OTP"));
	}
	
	public String getUUID() {
		return UUID;
	}	
	
	public String getOTP() {
		return OTP;
	}
	
	public String getMFA() {
		return MFA;
	}
}