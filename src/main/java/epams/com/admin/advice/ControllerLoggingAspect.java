package epams.com.admin.advice;

import java.time.LocalDate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import epams.com.admin.dto.LogViewDTO;
import epams.com.admin.repository.ViewLogRepository;
import epams.com.admin.util.ClientIpUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller의 메소드 호출을 로깅하는 Aspect.
 * 
 * @author 140024
 * @since 2024-04-02
 */
@Aspect
@Component
public class ControllerLoggingAspect {

    /** View 로그 저장소 */
    private final ViewLogRepository viewLogRepository;

    /** HTTP 요청 객체 */
    private final HttpServletRequest request;

    /**
     * ControllerLoggingAspect 생성자.
     * 
     * @param viewLogRepository View 로그 저장소
     * @param request           HTTP 요청 객체
     */
    public ControllerLoggingAspect(final ViewLogRepository viewLogRepository, final HttpServletRequest request) {
        this.viewLogRepository = viewLogRepository;
        this.request = request;
    }

    /**
     * 모든 @Controller 주석이 달린 클래스의 메소드를 포인트컷으로 정의.
     */
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerMethods() {
        // 포인트컷 메소드
    }

    /**
     * Controller 메소드 호출 후 로깅.
     * 
     * @param joinPoint 조인포인트 정보
     */
    @AfterReturning("controllerMethods()")
    public void logControllerCall(final JoinPoint joinPoint) {

        // 시그니쳐 정보
        final Signature signature = joinPoint.getSignature();

        // 컨트롤러 이름
        final String controllerName = signature.getDeclaringTypeName();

        // 메소드 이름
        final String methodName = signature.getName();

        // 호출 시간
        final LocalDate callTime = LocalDate.now();

        // 클라이언트 IP
        final String clientIp = ClientIpUtil.getClientIp(request);

        // 사용자 에이전트
        final String userAgent = request.getHeader("User-Agent");

        // 요청 URL
        final String requestUrl = request.getRequestURL().toString();

        // 로그 객체 생성 및 설정
        final LogViewDTO viewLog = new LogViewDTO();
        viewLog.setControllerName(controllerName);
        viewLog.setMethodName(methodName);
        viewLog.setCallTime(callTime);
        viewLog.setClientIp(clientIp);
        viewLog.setUserAgent(userAgent);
        viewLog.setRequestUrl(requestUrl);

        // 로그 저장소에 로그 삽입
        viewLogRepository.insert(viewLog);
    }
}
