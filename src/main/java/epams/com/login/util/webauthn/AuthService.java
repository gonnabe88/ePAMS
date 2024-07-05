package epams.com.login.util.webauthn;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.AssertionRequest;
import com.yubico.webauthn.AssertionResult;
import com.yubico.webauthn.FinishAssertionOptions;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.exception.AssertionFailedException;

import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.repository.LogRepository;
import epams.com.config.security.CustomGeneralRuntimeException;
import epams.com.login.repository.LoginRepository;
import epams.com.member.dto.IamUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 간편인증 서비스
 * 
 * @since 2024-06-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * 간편인증 문자열 (application.yml에서 설정)
     */
    @Value("${kdb.simpleAuthStr}")
    private String SIMPLEAUTH_STR;
    
    /**
     * 사용자 이름 파라미터
     */
    private static final String USERNAME_PARAM = "username";
    
    /**
     * RelyingParty 인스턴스
     */
    private final RelyingParty relyingParty;

    /**
     * LoginRepository 인스턴스
     */
    private final LoginRepository loginRepository;

    /**
     * LogRepository 인스턴스
     */
    private final LogRepository logRepository; 

    /**
     * 현재 인증된 사용자 정보 가져오기
     * 
     * @return 인증된 사용자 정보 또는 null
     */
    public Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new CustomGeneralRuntimeException("Authentication is null or anonymous");
        }
        return authentication;
    }

    /**
     * 로그인 시작 처리
     * 
     * @param username 사용자 이름
     * @param session  HTTP 세션
     * @return 로그인 요청에 대한 JSON 응답
     */
    public String startLogin(final String username, final HttpSession session) {
        final AssertionRequest request = relyingParty.startAssertion(StartAssertionOptions.builder()
            .username(username)
            .build());
        try {
            session.setAttribute(username, request.toJson());
            return request.toCredentialsGetJson();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    
    /**
     * 로그인 완료 요청 처리
     * @return 로그인 완료에 대한 응답
     */
    public ResponseEntity<?> finishLogin(final String credential, final String username, final Model model, final HttpSession session) {
        log.warn("finishLogin START");
        final IamUserDTO iamUserDTO = new IamUserDTO();
        iamUserDTO.setUsername(username);
        loginRepository.findByUserId(iamUserDTO);
        final Map<String, Object> response = new ConcurrentHashMap<>();
        HttpStatus status = HttpStatus.OK;
    
        final Object sessionData = session.getAttribute(iamUserDTO.getUsername());
        if (sessionData == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session data not found.");
        }
    
        try {
            final PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> pkc;
            pkc = PublicKeyCredential.parseAssertionResponseJson(credential);
    
            // String 데이터를 AssertionRequest 객체로 변환
            final String sessionDataString = (String) sessionData;
            final AssertionRequest request = AssertionRequest.fromJson(sessionDataString);
            
            final AssertionResult result = relyingParty.finishAssertion(FinishAssertionOptions.builder()
                .request(request)
                .response(pkc)
                .build());
            
            if (result.isSuccess()) {
                model.addAttribute(USERNAME_PARAM, iamUserDTO.getUsername());
                final SecurityContext context = SecurityContextHolder.createEmptyContext();
                final Authentication authentication = 
                    new UsernamePasswordAuthenticationToken(iamUserDTO.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
                logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), SIMPLEAUTH_STR, '1'));
                response.put("status", "OK");
                response.put("redirectUrl", "/index");
            } else {
                logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), SIMPLEAUTH_STR, '0'));
                response.put("status", "BAD_REQUEST");
                response.put("redirectUrl", "/login");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (JsonProcessingException e) {
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), SIMPLEAUTH_STR, '0'));
            log.error("Error processing JSON: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
        } catch (AssertionFailedException e) {
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), SIMPLEAUTH_STR, '0'));
            log.error("Assertion failed: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed", e);
        } catch (IOException e) {
            logRepository.insert(LogLoginDTO.getDTO(iamUserDTO.getUsername(), SIMPLEAUTH_STR, '0'));
            log.error("Failed to save credential: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save credential, please try again!", e);
        }
    
        return ResponseEntity.status(status).body(response);
    }
    
}
