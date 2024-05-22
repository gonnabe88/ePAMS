package com.kdb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.kdb.common.service.StorageProperties;
import com.kdb.common.service.StorageService;


import com.kdb.webauthn.configuration.WebAuthProperties;
import com.kdb.webauthn.RegistrationService;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EPamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPamsApplication.class, args);
	}	
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
	
	@Bean
	@Autowired
	public RelyingParty relyingParty(RegistrationService regisrationRepository, WebAuthProperties properties) {
		RelyingPartyIdentity rpIdentity = RelyingPartyIdentity.builder()
			.id(properties.getHostName())
			.name(properties.getDisplay())
			.build();

		return RelyingParty.builder()
			.identity(rpIdentity)
			.credentialRepository(regisrationRepository)
			.origins(properties.getOrigin())
			.build();
	}
}
