package epams.com.config.storage;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author K140024
 * @implNote 파일 저장소 서비스 인터페이스
 * @since 2024-06-11
 */
public interface StorageService {

    /**
     * 저장소를 초기화하는 메소드
     */
    void init();

    /**
     * 파일을 저장하는 메소드
     * 
     * @param file 저장할 파일
     */
    void store(MultipartFile file);

    /**
     * 저장소에 있는 모든 파일의 경로를 스트림으로 반환하는 메소드
     * 
     * @return 파일 경로의 스트림
     */
    Stream<Path> loadAll();

    /**
     * 파일 이름으로 파일 경로를 반환하는 메소드
     * 
     * @param filename 파일 이름
     * @return 파일 경로
     */
    Path load(String filename);

    /**
     * 파일 이름으로 파일을 리소스로 반환하는 메소드
     * 
     * @param filename 파일 이름
     * @return 파일 리소스
     */
    Resource loadAsResource(String filename);

    /**
     * 저장소에 있는 모든 파일을 삭제하는 메소드
     */
    void deleteAll();
}
