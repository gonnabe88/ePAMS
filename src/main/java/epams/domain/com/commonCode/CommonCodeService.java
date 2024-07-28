package epams.domain.com.commonCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCodeService {
	
    /**
     * @author K140024
     * @implNote CodeRepository 주입
     * @since 2024-04-26
     */
	private final CommonCodeRepository commonCodeRepository;

	/**
     * @author K140024
     * @implNote 전제 목록 조회
     * @since 2024-04-26
     */
    public List<CommonCodeDTO> findAll() {
    	return commonCodeRepository.findAll();
    }

    /**
     * @author K140024
     * @implNote 코드유형에 해당하는 코드 목록 조회
     * @since 2024-04-26
     */
    public List<CommonCodeDTO> findByCodeKind(CommonCodeDTO dto) {
        return commonCodeRepository.findByCodeKind(dto);
    }

    /**
     * @author K140024
     * @implNote 코드유형 & 코드에 해당하는 코드 목록 조회
     * @since 2024-04-26
     */
    public List<CommonCodeDTO> findByCodeKindAndCode(CommonCodeDTO dto) {
        return commonCodeRepository.findByCodeKindAndCode(dto);
    }

}
