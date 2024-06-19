
package epams.com.login.util.webauthn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import epams.com.login.repository.LoginRepository;
import epams.com.login.util.webauthn.authenticator.WebauthDetailDTO;
import epams.com.login.util.webauthn.user.WebauthUserDTO;
import epams.com.login.util.webauthn.utility.Utility;
import epams.com.member.dto.MemberDTO;
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
    


    @GetMapping("/login")
    public String loginPage() {
        return "webauthn/login";
    }


    
    @PostMapping("/welcome")
    public ResponseEntity<Map<String, String>> finishLogin(
        @RequestParam(value="credential") String credential,
        @RequestParam(value="username") String username,
        Model model,
        HttpSession session
    ) {
        Map<String, String> response = new HashMap<>();
        log.warn("Received username: " + username);
        MemberDTO ismemberDTO = loginRepository.findByUserId(username);
        try {
            log.warn("Processing /welcome request");
            PublicKeyCredential<AuthenticatorAssertionResponse, ClientAssertionExtensionOutputs> pkc;
            pkc = PublicKeyCredential.parseAssertionResponseJson(credential);

            String sessionAttribute = (String) session.getAttribute(username);
            if (sessionAttribute == null) {
                throw new AssertionFailedException("Session attribute is null");
            }

            log.warn("Session attribute: " + sessionAttribute);
            AssertionRequest request = AssertionRequest.fromJson(sessionAttribute);
            AssertionResult result = relyingParty.finishAssertion(FinishAssertionOptions.builder()
                .request(request)
                .response(pkc)
                .build());

            if (result.isSuccess()) {
                model.addAttribute("username", username);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                    ismemberDTO.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

                log.warn("Authentication successful for user: " + username);
                logRepository.insert(LogLoginDTO.getDTO(username, "간편인증", true));
                response.put("status", "success");
                response.put("redirectUrl", "/index");
                return ResponseEntity.ok(response);
            } else {
                log.warn("Authentication failed for user: " + username);
                logRepository.insert(LogLoginDTO.getDTO(username, "간편인증", false));
                response.put("status", "failure");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for user: " + username, e);
            logRepository.insert(LogLoginDTO.getDTO(username, "간편인증", false));
            response.put("status", "error");
            response.put("message", "Error processing JSON.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (AssertionFailedException e) {
            log.warn("Assertion failed for user: " + username, e);
            logRepository.insert(LogLoginDTO.getDTO(username, "간편인증", false));
            response.put("status", "error");
            response.put("message", "Authentication failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            log.error("Failed to save credential for user: " + username, e);
            logRepository.insert(LogLoginDTO.getDTO(username, "간편인증", false));
            response.put("status", "error");
            response.put("message", "Failed to save credential, please try again!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
