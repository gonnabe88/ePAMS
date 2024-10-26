package epams.domain.com.login.util.webauthn.service;

import com.yubico.webauthn.RelyingParty;

import epams.framework.exception.CustomGeneralRuntimeException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 간편인증 서비스
 * 
 * @since 2024-06-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDeleteService {

    /**
     * 간편인증 문자열 (application.yml에서 설정)
     */
    @Value("${kdb.simpleAuthStr}")
    private String SIMPLEAUTH_STR;
    
    /**
     * RelyingParty 인스턴스
     */
    private final RelyingParty relyingParty;

    /**
     * RegistrationService 인스턴스
     */
    private final RegistrationService service;

    /**
     * 현재 인증된 사용자 정보 가져오기
     * 
     * @return 인증된 사용자 정보 또는 null
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

    /**
     * 현재 로그인된 사용자의 인증정보를 모두 삭제합니다.
     * @since 2024-06-11
     */
    @Transactional
    public void deleteByUsername() {
        Authentication tempNullableVar = authentication();
        if(tempNullableVar != null) {
        	 service.getAuthRepository().deleteAllByUser_Username(tempNullableVar.getName());
        	 service.getUserRepo().deleteByUsername(tempNullableVar.getName());
        } else {
        	throw new CustomGeneralRuntimeException("로그인이 만료되었습니다. 로그인 후 사용해주세요");
        }
    }
}
