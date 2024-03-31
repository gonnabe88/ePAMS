package com.kdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private UserDetailsService userDetailsService;
    
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("[LOG] CustomAuthenticationProvider {} {}", username, password);
        // Perform your custom authentication logic here
        // Retrieve user details from userDetailsService and validate the credentials
        // You can throw AuthenticationException if authentication fails
        // Example: retrieving user details by username from UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Example: validating credentials
        if (!password.equals(userDetails.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }
        // Create a fully authenticated Authentication object
        Authentication authenticated = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        return authenticated;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        // Return true if this AuthenticationProvider supports the provided authentication class
    	log.info("[LOG] CustomAuthenticationProvider3");
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}