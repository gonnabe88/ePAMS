package com.kdb;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EPamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPamsApplication.class, args);
	}

	
}
