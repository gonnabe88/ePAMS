package epams;

import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import epams.domain.com.login.util.webauthn.configuration.WebAuthProperties;
import epams.domain.com.login.util.webauthn.service.RegistrationService;
import epams.framework.storage.StorageProperties;
import epams.framework.storage.StorageService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 140024
 * @since 2024-03-02
 * @implNote Spring Boot 메인 애플리케이션 클래스.
 */
@Slf4j
@NoArgsConstructor // 기본생성자
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class EPamsApplication  extends SpringBootServletInitializer {

	/**
	 * @author 140024
	 * @since 2024-03-02
	 * @implNote 메인 메서드. 애플리케이션 실행 진입점
	 */
	public static void main(final String[] args) {

		SpringApplication.run(EPamsApplication.class, args);

	}

	/**
	 * @author 140024
	 * @since 2024-06-23
	 * @implNote 3rd party WAS를 통한 실행을 위해 추가 (WAR배포)
	 */
	@Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return super.configure(application);
    }
	
	/**
	 * @author 140024
	 * @since 2024-04-02
	 * @implNote StorageService 초기화 빈. 애플리케이션 시작 시 스토리지 서비스를 초기화
	 */
	@Bean
	/* default */ CommandLineRunner init(final StorageService storageService) {

		return (args) -> {
			// storageService.deleteAll(); // 스토리지 초기화 시 모든 데이터를 삭제
			storageService.init(); // 스토리지 초기화
		};
	}

	/**
	 * @author 140024
	 * @since 2024-05-02
	 * @implNote relyingParty Baen 등록 WebAuthn(간편인증) 등록 및 인증을 처리하는 서비스
	 */
	@Bean
	@Autowired
	/* default */ RelyingParty relyingParty(final RegistrationService regisrationRepo, final WebAuthProperties properties) {
		
		final RelyingPartyIdentity rpIdentity = RelyingPartyIdentity.builder().id(properties.getHostName()) // Relying Party HostName
				.name(properties.getDisplay()) // Relying Party Display 설정
				.build();
		return RelyingParty.builder().identity(rpIdentity) // Relying Party Identity 설정
				.credentialRepository(regisrationRepo) // 등록 서비스 설정
				.origins(properties.getOrigin()) // 허용되는 원본(origin) 설정
				.build();
	}
}
