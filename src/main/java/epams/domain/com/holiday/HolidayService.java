package epams.domain.com.holiday;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 코드 service
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {
	
    /**
     * @author K140024
     * @implNote LangRepository 주입
     * @since 2024-04-26
     */
	private final HolidayRepository holidayRepository;

    /**
     * @author K140024
     * @implNote 휴일 목록 조회
     * @since 2024-04-26
     */
    public List<String> findholiYmd() {
        return holidayRepository.findholiYmd();
    }

}
