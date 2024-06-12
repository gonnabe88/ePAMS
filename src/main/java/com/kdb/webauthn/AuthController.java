
package com.kdb.webauthn;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kdb.common.dto.LogLoginDTO;
import com.kdb.common.dto.MemberDTO;
import com.kdb.common.repository.LogRepository;
import com.kdb.common.repository.LoginRepository;
import com.kdb.webauthn.authenticator.Authenticator;
import com.kdb.webauthn.user.AppUser;
import com.kdb.webauthn.utility.Utility;
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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/webauthn")
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final RelyingParty relyingParty;
    private final RegistrationService service;
    private final LoginRepository loginRepository;
    private final LogRepository logRepository;
    
	private Authentication Authentication() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    log.warn(authentication.getName());
	    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
	    return authentication;
	}

	/*
	 * AuthController(RegistrationService service, RelyingParty relyingPary) {
	 * this.relyingParty = relyingPary; this.service = service; }
	 */	 

    @GetMapping("/")
    public String index() {
        return "webauthn/index";
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return "webauthn/welcome";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        return "webauthn/register";
    }
    

    @PostMapping("/register")
    @ResponseBody
    public String newUserRegistration(
		@RequestParam(value="username") String username,
        HttpSession session
    ) {
    	//Authentication auth = Authentication();
    	//String username = auth.getName();
    	log.warn("register : "+username);
        AppUser existingUser = service.getUserRepo().findByUsername(username);
        List<Authenticator> existingAuthUser = service.getAuthRepository().findAllByUser(existingUser);
        if (existingAuthUser.isEmpty()) {
            UserIdentity userIdentity = UserIdentity.builder()
                .name(username)
                .displayName(username)
                .id(Utility.generateRandom(32))
                .build();
            AppUser saveUser = new AppUser(userIdentity);
            service.getUserRepo().save(saveUser);
            String response = newAuthRegistration(saveUser, session);
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username " + username + " already exists. Choose a new name.");
        }
    }

    @PostMapping("/registerauth")
    @ResponseBody
    public String newAuthRegistration(
        @RequestParam(value="user") AppUser user,
        HttpSession session
    ) {
        AppUser existingUser = service.getUserRepo().findByHandle(user.getHandle());
        if (existingUser != null) {
            UserIdentity userIdentity = user.toUserIdentity();           

            
            // AuthenticatorSelectionCriteria 설정 추가
            AuthenticatorSelectionCriteria authenticatorSelection = AuthenticatorSelectionCriteria.builder()
                .authenticatorAttachment(AuthenticatorAttachment.PLATFORM) // 플랫폼 인증기를 사용 (설정 시 기기의 플랫폼 인증기 우선)
                .residentKey(ResidentKeyRequirement.PREFERRED) // 레지던트 키를 선호
                .userVerification(UserVerificationRequirement.REQUIRED) // 사용자 검증을 필수로 설정
                .build();
            
            StartRegistrationOptions registrationOptions = StartRegistrationOptions.builder()
            .user(userIdentity)      
            .authenticatorSelection(authenticatorSelection) // authenticatorSelection 설정 적용     
            .build();
            
            
            PublicKeyCredentialCreationOptions registration = relyingParty.startRegistration(registrationOptions);
            try {
				session.setAttribute(userIdentity.getDisplayName(), registration.toJson());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String json = objectMapper.writeValueAsString(registration);
//                session.setAttribute(userIdentity.getDisplayName(), json);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("Failed to convert PublicKeyCredentialCreationOptions to JSON", e);
//            }
            
            try {
            	    String retr = registration.toCredentialsCreateJson();
            		log.warn("registration.toCredentialsCreateJson()");
            		log.warn(retr);
                    //return registration.toCredentialsCreateJson();
            		return retr;
            } catch (JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User " + user.getUsername() + " does not exist. Please register.");
        }
    }

    @PostMapping("/finishauth")
    @ResponseBody
    public ResponseEntity<String> finishRegisration(
        @RequestParam(value="credential") String credential,
        HttpSession session
    ) {
    	Authentication auth = Authentication();
    	String username = auth.getName();
            try {
                AppUser user = service.getUserRepo().findByUsername(username);
                log.warn(session.getAttribute(user.getUsername()).toString());
                PublicKeyCredentialCreationOptions requestOptions = (PublicKeyCredentialCreationOptions.fromJson((String) session.getAttribute(user.getUsername())) );
                if (requestOptions != null) {
                    PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> pkc =
                    PublicKeyCredential.parseRegistrationResponseJson(credential);
                    FinishRegistrationOptions options = FinishRegistrationOptions.builder()
                        .request(requestOptions)
                        .response(pkc)
                        .build();
                    RegistrationResult result = relyingParty.finishRegistration(options);
                    Authenticator savedAuth = new Authenticator(result, pkc.getResponse(), user, username);
                    service.getAuthRepository().save(savedAuth);
                    //return new ModelAndView("redirect:/popup", HttpStatus.SEE_OTHER);
                    return ResponseEntity.ok("Registration successful!");
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cached request expired. Try to register again!");
                }
            } catch (RegistrationFailedException e) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Registration failed.", e);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save credenital, please try again!", e);
            }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "webauthn/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String startLogin(
        @RequestParam(value="username") String username,
        HttpSession session
    ) {
    	log.warn("POST login");
        AssertionRequest request = relyingParty.startAssertion(StartAssertionOptions.builder()
            .username(username)
            .build());
        try {
            session.setAttribute(username, request.toJson());
            return request.toCredentialsGetJson();
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    
    @PostMapping("/welcome")
    public String finishLogin(
        @RequestParam(value="credential") String credential,
        @RequestParam(value="username") String username,
        Model model,
        HttpSession session
    ) {
    	//Authentication auth = Authentication();
    	//String username = auth.getName();
    	log.warn("welcome : " + username);
    	MemberDTO ismemberDTO = loginRepository.findByUserId(username);
        try {
        	log.warn("POST welcome");
            PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> pkc;
            pkc = PublicKeyCredential.parseAssertionResponseJson(credential);
            log.warn(session.getAttribute(username).toString());
            log.warn((String)session.getAttribute(username));
            AssertionRequest request = (AssertionRequest.fromJson((String) session.getAttribute(username)));
            AssertionResult result = relyingParty.finishAssertion(FinishAssertionOptions.builder()
                .request(request)
                .response(pkc)
                .build());
            if (result.isSuccess()) {
                model.addAttribute("username", username);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = 
                		new UsernamePasswordAuthenticationToken(ismemberDTO.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                session.setAttribute(HttpSessionSecurityContextRepository.
                        SPRING_SECURITY_CONTEXT_KEY, context);
                log.warn("isSuccess");
                logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "간편인증", true));
                return "/common/index";
            } else {
            	logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "간편인증", false));
                return "/common/login";
            }
        } catch (JsonProcessingException e) {
        	logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "간편인증", false));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing JSON.", e);
        } catch (AssertionFailedException e) {
        	logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "간편인증", false));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication failed", e);
        } catch (IOException e) {
        	logRepository.saveLoginLog(LogLoginDTO.getLogLoginDTO(username, "간편인증", false));
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save credenital, please try again!", e);
		}
    }    
}
