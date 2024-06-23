package epams.com.config.security;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.core.codec.EncodingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {


    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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
        // 2024-06-22 취약점 조치 (CWE-256 Unprotected Storage of Credentials)
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
