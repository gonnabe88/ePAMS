package com.kdb.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	
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
		Cookie[] cookies = request.getCookies();
		OTP = request.getParameter("OTP");
		MFA = request.getParameter("MFA");
		
		//log.warn("{} {}",request.getParameter("MFA"), request.getParameter("OTP"));
		for(int i=0; i < cookies.length; i++)
		{
			//log.warn("UUID {} {}, bool check {} ",cookies[i].getName(), cookies[i].getValue(), cookies[i].getName().matches("UUIDChk"));
			if (cookies[i].getName().matches("UUIDChk"))
			{
				log.warn(UUID);
				UUID = cookies[i].getValue();
			}
		}
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