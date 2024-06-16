package epams.com.login.service;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShaEncryptService {
    public String encrypt(String planText) throws Exception{
        try {
        	Decoder decoder = Base64.getDecoder();
        	byte[] decodedBytes = decoder.decode(planText);
        	String decodedStr = new String(decodedBytes, StandardCharsets.UTF_8);
        	log.warn(decodedStr);
        	
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            Encoder encoder = Base64.getEncoder();
            byte[] digest = md.digest(decodedStr.getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Fail to encrype SHA-256");
        }
    }
}
