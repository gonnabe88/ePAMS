
package epams.com.admin.advice;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import epams.com.admin.dto.LogViewDTO;
import epams.com.admin.repository.ViewLogRepository;
import epams.com.admin.util.ClientIpUtil;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerLoggingAspect {

	private final ViewLogRepository viewLogRepository;
	private final HttpServletRequest request;

	public ControllerLoggingAspect(ViewLogRepository viewLogRepository, HttpServletRequest request) {
		this.viewLogRepository = viewLogRepository;
		this.request = request;
	}

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerMethods() {
	}

	@AfterReturning("controllerMethods()")
	public void logControllerCall(JoinPoint joinPoint) {
		String controllerName = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		LocalDateTime callTime = LocalDateTime.now();
		String clientIp = ClientIpUtil.getClientIp(request);
		String userAgent = request.getHeader("User-Agent");
		String requestUrl = request.getRequestURL().toString();

		LogViewDTO viewLog = new LogViewDTO();
		viewLog.setControllerName(controllerName);
		viewLog.setMethodName(methodName);
		viewLog.setCallTime(callTime);
		viewLog.setClientIp(clientIp);
		viewLog.setUserAgent(userAgent);
		viewLog.setRequestUrl(requestUrl);

		viewLogRepository.insert(viewLog);
	}
}
