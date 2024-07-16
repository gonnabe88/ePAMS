package epams.framework.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author K140024
 * @implNote 파일 시스템을 이용한 저장소 서비스 구현 클래스
 * @since 2024-06-11
 */
@Service
public class FileSystemStorageService implements StorageService {

    /**
     * @author K140024
     * @implNote 파일 저장 위치
     * @since 2024-06-11
     */
    private final Path rootLocation;

    /**
     * @author K140024
     * @implNote 파일 저장 위치 확인하는 메소드
     * @since 2024-06-11
     */
    public FileSystemStorageService(final StorageProperties properties) {
        if (properties.getLocation().trim().isEmpty()) {
            throw new StorageException("File upload location cannot be empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * @author K140024
     * @implNote 파일을 저장하는 메서드
     * @since 2024-06-11
     */
    @Override
    public void store(final MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            final Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    /**
     * @author K140024
     * @implNote 저장된 모든 파일을 로드하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    /**
     * @author K140024
     * @implNote 파일 이름을 통해 파일 경로를 로드하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Path load(final String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * @author K140024
     * @implNote 파일을 리소스로 로드하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Resource loadAsResource(final String filename) {
        try {
            final Path file = load(filename);
            final Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /**
     * @author K140024
     * @implNote 저장된 모든 파일을 삭제하는 메서드
     * @since 2024-06-11
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /**
     * @author K140024
     * @implNote 저장소를 초기화하는 메서드
     * @since 2024-06-11
     */
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
