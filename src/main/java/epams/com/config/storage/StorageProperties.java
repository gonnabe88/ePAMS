package epams.com.config.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.NoArgsConstructor;

/**
 * @author K140024
 * @implNote 파일 시스템에 저장할 실제 경로 설정값 (application.yml)
 * @since 2024-06-11
 */
@NoArgsConstructor
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * @author K140024
     * @implNote 파일 시스템에 저장할 실제 경로
     * @since 2024-06-11
     */
    @Value("${kdb.filepath}")
    private String filepath;

    /**
     * @author K140024
     * @implNote 파일 경로를 반환하는 메소드
     * @since 2024-06-11
     * @return 파일 경로
     */
    public String getLocation() {
        return filepath;
    }

    /**
     * @author K140024
     * @implNote 파일 경로를 설정하는 메소드
     * @since 2024-06-11
     * @param location 파일 경로
     */
    public void setLocation(final String location) {
        this.filepath = location;
    }
}
