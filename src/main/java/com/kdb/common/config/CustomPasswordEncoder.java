package com.kdb.common.config;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                throw new RuntimeException();
            }
       //String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));  
       //return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("Custom match 입력 : "+ rawPassword + " 원본 : " + encodedPassword);
        //if(encode(rawPassword).equals(encodedPassword)) return true;
    	if(rawPassword.equals(encodedPassword)) return true;
        else return false;
    }
}
