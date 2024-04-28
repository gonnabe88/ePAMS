package com.kdb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.kdb.common.service.StorageProperties;
import com.kdb.common.service.StorageService;


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
}
