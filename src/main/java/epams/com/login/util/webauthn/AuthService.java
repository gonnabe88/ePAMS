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
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorAssertionResponse;
import com.yubico.webauthn.data.AuthenticatorAttachment;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.ClientAssertionExtensionOutputs;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.ResidentKeyRequirement;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.data.UserVerificationRequirement;
import com.yubico.webauthn.exception.AssertionFailedException;
import com.yubico.webauthn.exception.RegistrationFailedException;

import epams.com.admin.dto.LogLoginDTO;
import epams.com.admin.repository.LogRepository;
import epams.com.config.security.CustomGeneralRuntimeException;
import epams.com.login.repository.LoginRepository;
import epams.com.login.util.webauthn.authenticator.WebauthDetailDTO;
import epams.com.login.util.webauthn.user.WebauthUserDTO;
import epams.com.login.util.webauthn.utility.Utility;
import epams.com.member.dto.MemberDTO;
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
     * RegistrationService 인스턴스
     */
    private final RegistrationService service;

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
        log.warn("POST login");
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
     * 새로운 사용자 등록 요청 처리
     * 
     * @param username 사용자 이름
     * @param session  HTTP 세션
     * @return 등록 요청에 대한 JSON 응답
     */
    public String newUserRegistration(final String username, final HttpSession session) {
        final WebauthUserDTO existingUser = service.getWebauthUserRepository().findByUsername(username);        	
        final List<WebauthDetailDTO> existingAuthUser = service.getWebauthDetailRepository().findAllByUser(username);
        if (existingAuthUser.isEmpty()) {
            final UserIdentity userIdentity = UserIdentity.builder()
                .name(username)
                .displayName(username)
                .id(Utility.generateRandom(32))
                .build();
            final WebauthUserDTO saveUser = new WebauthUserDTO(userIdentity);
            service.getWebauthUserRepository().insertUpdate(saveUser);
            return newAuthRegistration(saveUser, session);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username " + username + " already exists. Choose a new name.");
        }
    }

    /**
     * 새로운 인증 등록 요청 처리
     * 
     * @param user    WebauthUserDTO 객체
     * @param session HTTP 세션
     * @return 등록 요청에 대한 JSON 응답
     */
    public String newAuthRegistration(final WebauthUserDTO user, final HttpSession session) {
        final WebauthUserDTO existingUser = service.getWebauthUserRepository().findByHandle(user.getHandle());
        if (existingUser != null) {
            final UserIdentity userIdentity = user.toUserIdentity();           

            final AuthenticatorSelectionCriteria authSelection = AuthenticatorSelectionCriteria.builder()
                .authenticatorAttachment(AuthenticatorAttachment.PLATFORM)
                .residentKey(ResidentKeyRequirement.PREFERRED)
                .userVerification(UserVerificationRequirement.REQUIRED)
                .build();

            final StartRegistrationOptions regOptions = StartRegistrationOptions.builder()
                .user(userIdentity)
                .authenticatorSelection(authSelection)
                .build();

            final PublicKeyCredentialCreationOptions registration = relyingParty.startRegistration(regOptions);
            try {
                session.setAttribute(userIdentity.getDisplayName(), registration.toJson());
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
            }

            try {
                return registration.toCredentialsCreateJson();
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User " + user.getUsername() + " does not exist. Please register.");
        }
    }

    /**
     * 등록 완료 요청 처리
     * 
     * @param credential 자격 증명
     * @param session    HTTP 세션
     * @return 등록 완료에 대한 응답
     */
    public ResponseEntity<String> finishRegistration(final String credential, final HttpSession session) {
        final Authentication auth = getAuthentication();
        final String username = auth.getName();
        try {
            final WebauthUserDTO user = service.getWebauthUserRepository().findByUsername(username);
            final PublicKeyCredentialCreationOptions requestOptions = PublicKeyCredentialCreationOptions.fromJson((String) session.getAttribute(user.getUsername()));
            if (requestOptions != null) {
                final PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> pkc =
                    PublicKeyCredential.parseRegistrationResponseJson(credential);
                final FinishRegistrationOptions options = FinishRegistrationOptions.builder()
                    .request(requestOptions)
                    .response(pkc)
                    .build();
                final RegistrationResult result = relyingParty.finishRegistration(options);
                final WebauthDetailDTO savedAuth = new WebauthDetailDTO(result, pkc.getResponse(), user);
                service.getWebauthDetailRepository().insert(savedAuth);
                return ResponseEntity.ok("Registration successful!");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cached request expired. Try to register again!");
            }
        } catch (RegistrationFailedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Registration failed.", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save credential, please try again!", e);
        }
    }

    /**
     * 로그인 완료 요청 처리
     * @return 로그인 완료에 대한 응답
     */
    public ResponseEntity<?> finishLogin(final String credential, final String username, final Model model, final HttpSession session) {
        final MemberDTO ismemberDTO = loginRepository.findByUserId(username);
        final Map<String, Object> response = new ConcurrentHashMap<>();
        HttpStatus status = HttpStatus.OK;

        try {
            log.warn("POST welcome");
            final PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> pkc;
            pkc = PublicKeyCredential.parseAssertionResponseJson(credential);
            final AssertionRequest request = AssertionRequest.fromJson((String) session.getAttribute(username));
            final AssertionResult result = relyingParty.finishAssertion(FinishAssertionOptions.builder()
                .request(request)
                .response(pkc)
                .build());
            if (result.isSuccess()) {
                model.addAttribute(USERNAME_PARAM, username);
                final SecurityContext context = SecurityContextHolder.createEmptyContext();
                final Authentication authentication = 
                    new UsernamePasswordAuthenticationToken(ismemberDTO.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
                log.warn("isSuccess");
                logRepository.insert(LogLoginDTO.getDTO(username, SIMPLEAUTH_STR, true));
                response.put("status", "OK");
                response.put("redirectUrl", "/index");
            } else {
                logRepository.insert(LogLoginDTO.getDTO(username, SIMPLEAUTH_STR, false));
                response.put("status", "BAD_REQUEST");
                response.put("redirectUrl", "/login");
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (JsonProcessingException e) {
            logRepository.insert(LogLoginDTO.getDTO(username, SIMPLEAUTH_STR, false));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
        } catch (AssertionFailedException e) {
            logRepository.insert(LogLoginDTO.getDTO(username, SIMPLEAUTH_STR, false));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed", e);
        } catch (IOException e) {
            logRepository.insert(LogLoginDTO.getDTO(username, SIMPLEAUTH_STR, false));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save credential, please try again!", e);
        }

        return ResponseEntity.status(status).body(response);
    }
}
