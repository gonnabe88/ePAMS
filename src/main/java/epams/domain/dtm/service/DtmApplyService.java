package epams.domain.dtm.service;

import epams.domain.com.apply.dto.ElaApplCDTO;
import epams.domain.com.apply.dto.ElaApplTrCDTO;
import epams.domain.com.apply.repository.ElaApplCRepository;
import epams.domain.com.apply.repository.ElaApplTrCRepository;
import epams.domain.dtm.dto.DtmHisDTO;
import epams.domain.dtm.repository.DtmHistoryRepository;
import epams.domain.dtm.repository.PreCheckProcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * @author 140024
 * @implNote Service
 * @since 2024-06-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DtmApplyService {

    /***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-06-09
	 */
	private final DtmHistoryRepository dtmHisRepo;

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final ElaApplCRepository elaApplCRepo;

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final ElaApplTrCRepository elaApplTrCRepo;

	/***
	 * @author 140024
	 * @implNote Repository 객체 생성
	 * @since 2024-08-04
	 */
	private final PreCheckProcRepository proCheckProcRepo;
    
	/***
	 * @author 140024
	 * @implNote 근태신청
	 * @since 2024-06-09
	 */
	@Transactional
    public void insert(final DtmHisDTO dtmHisDTO) {

		// 사전검증 프로시저
		//final DtmApplCheckProcDTO preCheckProcDTO = new DtmApplCheckProcDTO(dtmHisDTO.getEmpId(), dtmHisDTO.getStaYmd(), dtmHisDTO.getEndYmd(), null); // DtmApplCheckProcDTO(사전검증) 객체 생성
		//proCheckProcRepo.callPreCheckProc(preCheckProcDTO); // 사전검증 프로시저 호출
		//log.info("result: {}", preCheckProcDTO.getResult());

		// 신청서 추가
		final ElaApplCDTO elaApplCDTO = new ElaApplCDTO(dtmHisDTO.getEmpId(), "DTM01", "121"); // ElaApplCDTO(신청서) 객체 생성
		final long applId = elaApplCRepo.insert(elaApplCDTO); // 신청서 추가 후 applId 반환

		// 근태신청 추가
		dtmHisDTO.setApplId(applId); // DtmHisDTO(근태신청) 객체에 신청서 ID 설정
        dtmHisRepo.insert(dtmHisDTO); // 근태신청 추가

		// 신청서결재내역 추가
		final ElaApplTrCDTO elaApplTrCDTO = new ElaApplTrCDTO(dtmHisDTO.getEmpId(), "1", applId, 1); // ElaApplTrCDTO(신청서결재내역) 객체 생성
		elaApplTrCRepo.insert(elaApplTrCDTO); // 신청서결재내역 추가

    }
}
