package epams.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig 클래스는 WebMvcConfigurer 인터페이스를 구현한 설정 클래스입니다.
 * 이 클래스는 정적 리소스 핸들러를 추가하는 역할을 수행합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * addResourceHandlers 메서드는 리소스 핸들러를 등록하는 역할을 수행합니다.
     * 모든 요청에 대해 classpath:/static/ 경로에서 정적 리소스를 제공합니다.
     *
     * @param registry 리소스 핸들러 레지스트리 객체
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
