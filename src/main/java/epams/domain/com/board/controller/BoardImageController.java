package epams.domain.com.board.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import epams.domain.com.board.dto.BoardImageDTO;
import epams.domain.com.board.service.BoardImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 게시판 controller
 * @since 2024-04-26
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardImageController {

    /**
     * @author K140024
     * @implNote file 시스템에 저장할 실제 경로 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.filepath}")
    private String filepath;
    
    /**
     * @author K140024
     * @implNote list 화면에 노출할 게시물 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.listBrdCnt}")
    private int listBrdCnt;
    
    /**
     * @author K140024
     * @implNote 모든 페이지네이션 시 노출할 최대 버튼 수 설정값 (application.yml)
     * @since 2024-06-11
     */
    @Value("${kdb.maxPageBtn}")
    private int maxPageBtn;
    
    /**
     * @author K140024
     * @implNote 게시글 관련 서비스
     * @since 2024-04-26
     */
    private final BoardImageService boardImgService;

    /**
     * @author K140024
     * @implNote 이미지 업로드 처리 메소드
     * @since 2024-04-26
     */
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") final MultipartFile file) {
        final String uploadDir = filepath; // 이미지 저장 경로 설정
        ResponseEntity<?> responseEntity = ResponseEntity.status(500).body("Error uploading file");
        try {
            // 파일명 검증 취약점 조치 CWE-23 "문자/숫자/,/-/_" 외 모든 단어는 _로 대체
            final String originalFilename = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            final String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            final Path path = Paths.get(uploadDir).resolve(storedFilename).normalize();
                        	
            // 업로드 디렉토리 경로와 업로드된 파일 경로가 동일한 디렉토리에 있는지 확인
            if (path.startsWith(uploadDir)) {
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                final BoardImageDTO boardImageDTO = new BoardImageDTO();
                boardImageDTO.setOriginalFileName(originalFilename);
                boardImageDTO.setStoredFileName(storedFilename);
                boardImgService.saveBoardImage(boardImageDTO);

                final String fileUrl = "/board/upload/image/" + storedFilename;

                responseEntity = ResponseEntity.ok().body("{\"location\":\"" + fileUrl + "\"}");           
            }

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error uploading file", e);
        }
        return responseEntity;
    }

    /**
     * @author K140024
     * @implNote 업로드된 이미지를 가져오는 메소드
     * @since 2024-04-26
     */
    @GetMapping("/upload/image/{storedFileName}")
    public ResponseEntity<Resource> getBoardImageById(@PathVariable("storedFileName") final String storedFileName) {
        ResponseEntity<Resource> responseEntity = ResponseEntity.status(500).body(null);

        try {
            // 파일 이름 검증: 경로 탐색 패턴(../) 차단 및 허용된 문자만으로 구성되었는지 확인
            // CWE-918 - Server-Side Request Forgery (SSRF)
            if (storedFileName.contains("..") || !storedFileName.matches("[a-zA-Z0-9._-]+")) {
                return ResponseEntity.badRequest().build();
            }

            // 파일 경로 생성 및 파일 존재 여부 확인
            // CWE-918 - Server-Side Request Forgery (SSRF)
            final Path filePath = Paths.get(filepath).resolve(storedFileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());

            if (!filePath.startsWith(Paths.get(filepath).normalize())) {
                // 파일 경로가 기본 디렉토리 경로를 벗어나는 경우 접근 차단
                // CWE-918 - Server-Side Request Forgery (SSRF)
                return ResponseEntity.badRequest().build();            }

            if (resource.exists() && resource.isReadable()) {
                responseEntity = ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storedFileName + "\"")
                        .body(resource);
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error reading file", e);
        }
        return responseEntity;
    }
}