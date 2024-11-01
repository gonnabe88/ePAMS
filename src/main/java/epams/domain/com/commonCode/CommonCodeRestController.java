package epams.domain.com.commonCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/***
 * @author 140024
 * @implNote Rest API 요청처리를 위한 컨트롤러
 * @since 2024-06-09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commoncode/")
public class CommonCodeRestController {

    /***
     * @author 140024
     * @implNote 코드 서비스
     * @since 2024-06-09
     */
    private final CommonCodeService commonCodeService;

    /***
     * @author 140024
     * @implNote 모든 코드 데이터를 검색
     * @since 2024-06-09
     */
    @GetMapping("/code/all")
    public ResponseEntity<List<CommonCodeDTO>> findAll() throws IOException {
        final List<CommonCodeDTO> dtos = commonCodeService.findAll();
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 코드유형에 해당하는 코드 목록 조회
     * @since 2024-06-09
     */
    @GetMapping("/code/{codeKind}/")
    public ResponseEntity<List<CommonCodeDTO>> findByCodeKind(@PathVariable("codeKind") String codeKind) throws IOException {
        final List<CommonCodeDTO> dtos = commonCodeService.findByCodeKind(new CommonCodeDTO(codeKind));
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 140024
     * @implNote 코드유형 & 코드에 해당하는 코드 목록 조회
     * @since 2024-06-09
     */
    @GetMapping("/code/{codeKind}/{code}")
    public ResponseEntity<List<CommonCodeDTO>> findByCodeKindAndCode(@PathVariable("codeKind") String codeKind, @PathVariable("code") String code) throws IOException {
        final List<CommonCodeDTO> dtos = commonCodeService.findByCodeKindAndCode(new CommonCodeDTO(codeKind, code));
        return ResponseEntity.ok(dtos);
    }

    /***
     * @author 210058
     * @implNote 코드유형 & 코드에 해당하는 코드명 조회
     * @since 2024-09-03
     */
    @GetMapping("/common/getCodeName")
    public ResponseEntity<String> getCodeName(@RequestParam String codeKind, @RequestParam String code) {
        String codeName = commonCodeService.getCodeName(codeKind, code);
        if (codeName != null) {
            return ResponseEntity.ok(codeName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}