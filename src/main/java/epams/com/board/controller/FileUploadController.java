package epams.com.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import epams.com.config.storage.StorageFileNotFoundException;
import epams.com.config.storage.StorageService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 파일 업로드 및 다운로드를 처리하는 컨트롤러 클래스
 * @since 2024-06-11
 */
@Slf4j
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    /**
     * @author K140024
     * @implNote 파일 저장소 서비스 주입
     * @since 2024-06-11
     */
    private final StorageService storageService;

    public FileUploadController(final StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * @author K140024
     * @implNote 업로드된 파일 목록을 반환하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/")
    public String listUploadedFiles(final Model model) throws IOException {
        model.addAttribute("files",
                storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                        .collect(Collectors.toList()));

        return "/common/list";
    }

    /**
     * @author K140024
     * @implNote 파일을 다운로드하는 메서드
     * @since 2024-06-11
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable final String filename) {
        final Resource file = storageService.loadAsResource(filename);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * @author K140024
     * @implNote 파일을 업로드하는 메서드
     * @since 2024-06-11
     */
    @PostMapping("/")
    public @ResponseBody String upload(final MultipartHttpServletRequest request,
            @RequestParam final HashMap<String, Object> parameter) {
        return "/common/list";
    }

    /**
     * @author K140024
     * @implNote 파일을 찾을 수 없는 경우 예외를 처리하는 메서드
     * @since 2024-06-11
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(final StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
