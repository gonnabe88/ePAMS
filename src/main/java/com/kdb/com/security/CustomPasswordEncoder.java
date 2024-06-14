package com.kdb.com.security;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.core.codec.EncodingException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                Encoder encoder = Base64.getEncoder();
                byte[] digest = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
                //System.out.println("Custom encoded "+digest);
                return encoder.encodeToString(digest);
            } catch (Exception e) {
                e.printStackTrace();
                throw new EncodingException("Fail to encode SHA-256 ");
            }
       //String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));  
       //return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //if(encode(rawPassword).equals(encodedPassword)) return true;
    	if(rawPassword.equals(encodedPassword)) return true;
        else return false;
    }
}
