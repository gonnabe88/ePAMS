package epams.com.login.util.webauthn.configuration;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Webauthn 관련 설정을 제공하는 클래스
 * Spring Boot의 ConfigurationProperties를 사용하여 'authn' 프리픽스가 붙은 설정 값을 바인딩합니다.
 * @author K140024
 * @implNote Webauthn 관련 유틸리티 클래스를 제공하는 서비스
 * @since 2024-06-11
 */
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "authn")
@Getter
@Setter
public class WebAuthProperties {

    /**
     * 호스트 이름 설정 값
     */
    private String hostName;

    /**
     * 디스플레이 이름 설정 값
     */
    private String display;

    /**
     * 크리덴셜
     */
    private Set<String> origin;

}
