package epams.framework.session;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import epams.framework.exception.CustomGeneralRuntimeException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/session")
public class SessionRestController {
    
    /**
     * @author K140024
     * @implNote 현재 인증된 사용자 정보를 가져오는 메소드
     * @since 2024-04-26
     */
    private Authentication authentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Authentication result;
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            result = null;
        } else {
            result = authentication;
        }
        return result;
    }    

    @GetMapping("/check-session-auth")
    public ResponseEntity<Map<String, Object>> checkSessionAuth(HttpSession session) {

        Map<String, Object> response = new ConcurrentHashMap<>();

        log.warn(session.getAttributeNames().toString());

        try {
        	Authentication tempNullableVar = authentication();
        	if(tempNullableVar != null) {
	            if (tempNullableVar.getName() != null) {
	                response.put("sessionValid", true);
	            }
	            else {
	                response.put("sessionValid", false);
	            }
        	} else {
        		response.put("sessionValid", false);
        	}
        } catch (CustomGeneralRuntimeException e) {
            // 런타임 예외 처리
            // e.printStackTrace();
        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }       

        return new ResponseEntity<>(response, HttpStatus.OK);
    }    

    @GetMapping("/check-session-valid")
    public ResponseEntity<Map<String, Object>> checkSessionValid(HttpSession session) {

        Map<String, Object> response = new ConcurrentHashMap<>();
        if(session == null || session.isNew()){
            response.put("sessionValid", false);
        } else {
            response.put("sessionValid", true);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }  
}
