package epams.domain.com.constValue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import epams.domain.com.commonCode.CommonCodeDTO;

import java.util.List;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConstValueService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final ConstValueRepository constValueRepository;

    /**
     * @author K140024
     * @implNote 코드유형에 해당하는 코드 목록 조회
     * @since 2024-04-26
     */
    public String findConstValue(String unitCd, String constKind) {
        return constValueRepository.findConstValue(new ConstValueDTO(unitCd, constKind));
    }

}
