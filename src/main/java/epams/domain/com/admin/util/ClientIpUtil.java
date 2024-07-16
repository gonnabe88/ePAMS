package epams.domain.com.admin.util;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 140024
 * @since 2024-04-02
 * @implNote Client IP주소를 Header에서 파싱하여 가져오기 위한 Class
 */
public final class ClientIpUtil {

	/**
	 * @author 140024
	 * @since 2024-04-02
	 * @implNote unknown 리터럴 상수 
	 */
	private static final String UNKNOWN = "unknown";
	
	/**
	 * @author 140024
	 * @since 2024-06-22
	 * @implNote 인스턴스화를 방지하기 위한 private 생성자
	 */
    private ClientIpUtil() {
        throw new UnsupportedOperationException("Utility class");
    }
	
	/**
	 * @author 140024
	 * @since 2024-04-02
	 * @implNote Client IP주소를 Header에서 파싱하여 가져오기 위한 Method
	 */
	public static String getClientIp(final HttpServletRequest request) {
		
		String ipAddr = UNKNOWN;
		
        final List<String> headers = Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        );
        
        for (final String header : headers) {
            ipAddr = getHeaderIp(request, header);
            
            if (!isValidIp(ipAddr)) {
                ipAddr = request.getRemoteAddr();
            }
        }
        
        return ipAddr;
    }
    
	/**
	 * @author 140024
	 * @since 2024-04-02
	 * @implNote HeaderIP를 가져오는 Method
	 */
    private static String getHeaderIp(final HttpServletRequest request, final String header) {
        String ipAddr = request.getHeader(header);
        if ("X-Forwarded-For".equals(header) && ipAddr != null && ipAddr.contains(",")) {
            ipAddr = ipAddr.split(",")[0].trim();
        }
        return ipAddr;
    }
    
	/**
	 * @author 140024
	 * @since 2024-04-02
	 * @implNote 최종적으로 IP를 검증하는 Method
	 */
    private static boolean isValidIp(final String ipAddr) {
        return ipAddr != null && ipAddr.length() != 0 && !UNKNOWN.equalsIgnoreCase(ipAddr);
    }
}