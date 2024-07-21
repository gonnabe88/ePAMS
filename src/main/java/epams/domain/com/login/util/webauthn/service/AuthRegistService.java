package epams.domain.com.login.util.webauthn.service;

import java.io.IOException;
import java.util.List;

import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.user.AppUser;
import epams.domain.com.login.util.webauthn.utility.Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yubico.webauthn.FinishRegistrationOptions;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AuthenticatorAttachment;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.ResidentKeyRequirement;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.data.UserVerificationRequirement;
import com.yubico.webauthn.exception.RegistrationFailedException;

import epams.framework.security.CustomGeneralRuntimeException;
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
public class AuthRegistService {

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
    public Authentication getAuthentication() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new CustomGeneralRuntimeException("Authentication is null or anonymous");
        }
        return authentication;
    }

    /**
     * 새로운 사용자 등록 요청 처리
     * 
     * @param username 사용자 이름
     * @param session  HTTP 세션
     * @return 등록 요청에 대한 JSON 응답
     */
    @PostMapping("/register")
    @ResponseBody
    public String newUserRegistration(
		final @RequestParam("username") String username,
        final HttpSession session
    ) {
        final AppUser existingUser = service.getUserRepo().findByUsername(username);
        final List<Authenticator> existingAuthUser = service.getAuthRepository().findAllByUser(existingUser);
        final Utility util = new Utility();
        if (existingAuthUser.isEmpty()) {
            final UserIdentity userIdentity = UserIdentity.builder()
                .name(username)
                .displayName(username)
                .id(util.generateRandom(32))
                .build();
            final AppUser saveUser = new AppUser(userIdentity.getName(), userIdentity.getDisplayName(), userIdentity.getId());
            service.getUserRepo().save(saveUser);
            return newAuthRegistration(saveUser, session);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username " + username + " already exists. Choose a new name.");
        }
    }

    /**
     * 새로운 인증 등록 요청 처리
     * 
     * @param user    AppUser 객체
     * @param session HTTP 세션
     * @return 등록 요청에 대한 JSON 응답
     */
    public String newAuthRegistration(final AppUser user, final HttpSession session) {
        final AppUser existingUser = service.getUserRepo().findByHandle(user.getHandle());
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User " + user.getUsername() + "이미 등록된 사용자입니다.");
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
            final AppUser user = service.getUserRepo().findByUsername(username);
            
            final PublicKeyCredentialCreationOptions requestOptions = PublicKeyCredentialCreationOptions.fromJson((String) session.getAttribute(user.getUsername()));
            
            if (requestOptions != null) {
                final PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> pkc = PublicKeyCredential.parseRegistrationResponseJson(credential);
                final FinishRegistrationOptions options = FinishRegistrationOptions.builder()
                    .request(requestOptions)
                    .response(pkc)
                    .build();
                final AuthenticatorAttestationResponse response = pkc.getResponse();
                final RegistrationResult result = relyingParty.finishRegistration(options);
                final Authenticator savedAuth = new Authenticator(
                		result.getKeyId().getId(),
                		result.getPublicKeyCose(),
                		response.getAttestation().getAuthenticatorData().getAttestedCredentialData().get().getAaguid(),
                		result.getSignatureCount(), 
                		user);
                service.getAuthRepository().save(savedAuth);
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
    
}
