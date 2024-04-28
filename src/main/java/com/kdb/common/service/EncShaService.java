package com.kdb.common.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncShaService {
    public String encrypt(String planText) throws Exception{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            Encoder encoder = Base64.getEncoder();
            byte[] digest = md.digest(planText.getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Fail to encrype SHA-256");
        }
    }
}
