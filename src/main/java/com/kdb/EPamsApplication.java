package com.kdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.kdb.com.login.util.webauthn.RegistrationService;
import com.kdb.com.login.util.webauthn.configuration.WebAuthProperties;
import com.kdb.com.service.StorageProperties;
import com.kdb.com.service.StorageService;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EPamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EPamsApplication.class, args);
	}	
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll(); 
			storageService.init();
		};
	}
	
	@Bean
	@Autowired
	RelyingParty relyingParty(RegistrationService regisrationRepository, WebAuthProperties properties) {
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
