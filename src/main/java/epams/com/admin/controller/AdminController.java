package epams.com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 관리자용 화면 controller
 * @since 2024-06-09
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
    /***
     * @author 140024
     * @implNote 헬스 엔드포인트 자동 주입
     * @since 2024-06-09
     */
    @Autowired
    private HealthEndpoint healthEndpoint;    
    
    /***
     * @author 140024
     * @implNote 정보 엔드포인트 자동 주입
     * @since 2024-06-09
     */
    @Autowired
    private InfoEndpoint infoEndpoint;

    /***
     * @author 140024
     * @implNote 메트릭스 엔드포인트 자동 주입
     * @since 2024-06-09
     */
    @Autowired
    private MetricsEndpoint metricsEndpoint;

    /***
     * @author 140024
     * @implNote 객체 매퍼 자동 주입
     * @since 2024-06-09
     */
    @Autowired
    private ObjectMapper objectMapper;

    /***
     * @author 140024
     * @implNote 로거 엔드포인트
     * @since 2024-06-09
     */
    private final LoggersEndpoint loggersEndpoint;

	/***
	 * @author 140024
	 * @implNote 코드관리 (admin/code.html)
	 * @since 2024-06-09
	 */
    @GetMapping("/code")
    public String code(HttpServletRequest request) {
        return "/admin/code";
    }
    
	/***
	 * @author 140024
	 * @implNote 화면관리 (admin/page.html)
	 * @since 2024-06-09
	 */
    @GetMapping("/page")
    public String html(HttpServletRequest request) {
        return "/admin/page";
    }
    
	/***
	 * @author 140024
	 * @implNote 사용자관리 (admin/user.html)
	 * @since 2024-06-09
	 */
    @GetMapping("/user")
    public String user(HttpServletRequest request) {
        return "/admin/member";
    }
 
	/***
	 * @author 140024
	 * @implNote 로그인 이력 (admin/login.html)
	 * @since 2024-06-09
	 */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "/admin/login";
    }
 
	/***
	 * @author 140024
	 * @implNote spring acurator 제공 기본 상태체크 (admin/health.html)
	 * @since 2024-06-09
	 */
    @GetMapping("/health")
    public String health(final Model model) {
    	
        // Health data
        model.addAttribute("health", healthEndpoint.health());
        
        // Metrics data
        model.addAttribute("metrics", metricsEndpoint.listNames());
        
        // Loggers data
        final LoggersEndpoint.LoggersDescriptor loggersDescriptor = loggersEndpoint.loggers();
        model.addAttribute("loggers", loggersDescriptor.getLoggers());
    	
        return "/admin/actuator";
    }
    
}