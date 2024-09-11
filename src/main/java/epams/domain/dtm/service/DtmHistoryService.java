package epams.domain.dtm.service;

import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.dto.DtmSearchDTO;
import epams.domain.dtm.repository.DtmHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote Service
 * @since 2024-06-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DtmHistoryService {

    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmHistoryRepository dtmHisRepo;

	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
	public Page<DtmHisDTO> findByCondition(final Pageable pageable, final DtmSearchDTO searchDTO) {

		// 페이지네이션을 위한 페이지 번호, 페이지 크기, 오프셋 계산
		final int page = pageable.getPageNumber() - 1; // 페이지 번호
		final int pageSize = pageable.getPageSize(); // 페이지 크기
		final int offset = page * pageSize; // 오프셋 계산
		searchDTO.setLimit(pageSize);
		searchDTO.setOffset(offset);

		// MyBatis 쿼리를 통해 데이터 조회
		final List<DtmHisDTO> dtmHisDTOs = dtmHisRepo.findByCondition(searchDTO);

		// 각 DTO 객체에 대해 status를 갱신
		dtmHisDTOs.forEach(dto -> {
			String status = dto.calculateStatus(dto.getStaYmd(), dto.getStaHm(), dto.getEndYmd(), dto.getEndHm());
			dto.setStatus(status);
		});

		final long totalElements = dtmHisRepo.countByCondition(searchDTO); // 전체 데이터 개수를 조회하는 메소드 필요
		return new PageImpl<>(dtmHisDTOs, PageRequest.of(page, pageSize), totalElements);
	}
}
