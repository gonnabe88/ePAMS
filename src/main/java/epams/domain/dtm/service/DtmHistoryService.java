package epams.domain.dtm.service;

import epams.domain.dtm.dto.DtmHisDTO;
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
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmHistoryRepository dtmHisRepo;
    
	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
    public Page<DtmHisDTO> findByEmpId(final Pageable pageable, final DtmHisDTO dto) {
    	final int page = pageable.getPageNumber() - 1; // 페이지 번호
    	final int pageSize = pageable.getPageSize(); // 페이지 크기
        final int offset = page * pageSize; // 오프셋 계산
		dto.setLimit(pageSize);
		dto.setOffset(offset);
        final List<DtmHisDTO> dtos = dtmHisRepo.findByEmpId(dto)
				.stream()
				.map(vo -> new DtmHisDTO(
						vo.getDtmHisId(),
						vo.getEmpId(),
						vo.getDtmKindCd(),
						vo.getDtmReasonCd(),
						vo.getStaYmd(),
						vo.getStaHm(),
						vo.getEndYmd(),
						vo.getEndHm(),
						vo.getDtmReason(),
						vo.getDestPlc(),
						vo.getTelno(),
						vo.getChildNo(),
						vo.getApplId(),
						vo.getStatCd(),
						vo.getFinalApprYmd(),
						vo.getModiType(),
						vo.getModiReason(),
						vo.getModiDtmHisId(),
						vo.getModUserId(),
						vo.getModDate(),
						vo.getTzCd(),
						vo.getTzDate(),
						vo.getCompanyNm(),
						vo.getDocument(),
						vo.getAdUseYn(),
						vo.getMailSendYn(),
						vo.getEsbAskDt(),
						vo.getChildBirthYmd(),
						vo.getDtmStoreYn(),
						vo.getFileId(),
						vo.getSealMgrYn(),
						vo.getSecuMgrYn(),
						vo.getInfoMgrYn(),
						vo.getSafeMgrYn(),
						vo.getPlaceCd(),
						dto.getLimit(),
						dto.getOffset()
				))
				.collect(Collectors.toList());
        final long totalElements = dtmHisRepo.countById(dto); // 전체 데이터 개수를 조회하는 메소드 필요
        return new PageImpl<>(dtos, PageRequest.of(page, pageSize), totalElements);
    }  

}
