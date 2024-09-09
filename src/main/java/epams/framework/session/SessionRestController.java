package epams.framework.session;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;


@RestController
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

    @GetMapping("/check-session")
    public Map<String, Object> checkSession(HttpSession session) {

        Map<String, Object> response = new ConcurrentHashMap<>();

        if (authentication().getName() != null) {
            response.put("sessionValid", true);
        }
        else {
            response.put("sessionValid", false);
        }


        // Enumeration<String> attr = session.getAttributeNames();
        // while (attr.hasMoreElements()) {
        //     String attrName = attr.nextElement();
        //     Object attrVal = session.getAttribute(attrName);
        //     response.put(attrName, attrVal);
        // }

        return response;
    }    
}
