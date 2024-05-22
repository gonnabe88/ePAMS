/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kdb.webauthn;

import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.util.Base64UrlUtil;
import com.webauthn4j.util.UUIDUtil;
import com.webauthn4j.util.exception.WebAuthnException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Login controller
 */
@Slf4j
@SuppressWarnings("SameReturnValue")
@Controller
public class WebAuthnSampleController {

	private final Log logger = LogFactory.getLog(getClass());

	private static final String REDIRECT_LOGIN = "redirect:/login";

	private static final String VIEW_SIGNUP_SIGNUP = "/signup/signup";

	private static final String VIEW_DASHBOARD_DASHBOARD = "/dashboard/dashboard";

	private static final String VIEW_LOGIN_LOGIN = "login/login";

	private static final String VIEW_LOGIN_AUTHENTICATOR_LOGIN = "/login/authenticator-login";


	@Autowired
	private PasswordEncoder passwordEncoder;

	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

	@GetMapping(value = "/signup")
	public String template(Model model) {
		UserCreateForm userCreateForm = new UserCreateForm();
		UUID userHandle = UUID.randomUUID();
		String userHandleStr = Base64UrlUtil.encodeToString(UUIDUtil.convertUUIDToBytes(userHandle));
		userCreateForm.setUserHandle(userHandleStr);
		model.addAttribute("userForm", userCreateForm);
		return VIEW_SIGNUP_SIGNUP;
	}

	@PostMapping(value = "/signup")
	public String create(HttpServletRequest request, @Valid @ModelAttribute("userForm") UserCreateForm userCreateForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		try {
			if (result.hasErrors()) {
				model.addAttribute("errorMessage", "Your input needs correction.");
				logger.debug("User input validation failed.");
				return VIEW_SIGNUP_SIGNUP;
			}

			log.info(userCreateForm.getAuthenticator().getClientDataJSON());
			log.info(userCreateForm.getAuthenticator().getAttestationObject());
			log.info(userCreateForm.getAuthenticator().getTransports().toString());
			log.info(userCreateForm.getAuthenticator().getClientExtensions());

			String username = userCreateForm.getUsername();
			String password = passwordEncoder.encode(userCreateForm.getPassword());
			boolean singleFactorAuthenticationAllowed = userCreateForm.isSingleFactorAuthenticationAllowed();
			List<GrantedAuthority> authorities;
			if(singleFactorAuthenticationAllowed){
				authorities = Collections.singletonList(new SimpleGrantedAuthority("SINGLE_FACTOR_AUTHN_ALLOWED"));
			}
			else {
				authorities = Collections.emptyList();
			}
			User user = new User(username, password, authorities);

		}
		catch (RuntimeException ex){
			model.addAttribute("errorMessage", "Registration failed by unexpected error.");
			logger.debug("Registration failed.", ex);
			return VIEW_SIGNUP_SIGNUP;
		}

		redirectAttributes.addFlashAttribute("successMessage", "User registration finished.");
		return REDIRECT_LOGIN;
	}

	@GetMapping(value = "/wlogin")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return VIEW_LOGIN_LOGIN;
//		if (authenticationTrustResolver.isAnonymous(authentication)) {
//			return VIEW_LOGIN_LOGIN;
//		} else {
//			return VIEW_LOGIN_AUTHENTICATOR_LOGIN;
//		}
	}


}
