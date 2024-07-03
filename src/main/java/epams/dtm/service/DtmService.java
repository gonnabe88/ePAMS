package epams.dtm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epams.dtm.dto.BasePeriodDTO;
import epams.dtm.dto.DtmApplDTO;
import epams.dtm.repository.DtmRepository;
import lombok.RequiredArgsConstructor;
import groovy.util.logging.Slf4j;

/***
 * @author 140024
 * @implNote Service
 * @since 2024-06-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DtmService {

    /***
	 * @author 140024
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmRepository dtmRepository;
    
	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
    public Page<DtmApplDTO> findAllByPeriod(final Pageable pageable, final BasePeriodDTO period) {
    	final int page = pageable.getPageNumber() - 1; // 페이지 번호
    	final int pageSize = pageable.getPageSize(); // 페이지 크기
        period.setLimit(pageSize);
        final int offset = page * pageSize; // 오프셋 계산
        period.setOffset(offset);
        final List<DtmApplDTO> dtmapplDTOs = dtmRepository.findAllByPeriod(period);
        final long totalElements = dtmRepository.countAllByPeriod(period); // 전체 데이터 개수를 조회하는 메소드 필요
        return new PageImpl<>(dtmapplDTOs, PageRequest.of(page, pageSize), totalElements);
    }  

}
