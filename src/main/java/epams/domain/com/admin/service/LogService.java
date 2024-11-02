package epams.domain.com.admin.service;

import java.util.List;

import epams.domain.com.admin.dto.LogLoginDTO;
import org.springframework.stereotype.Service;

import epams.domain.com.admin.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리 로직 처리를 위한 service
 * @since 2024-06-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {
	
	/***
	 * @author 140024
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final LogRepository logRepository;

	/***
	 * @author 140024
	 * @implNote 특정 사용자의 로그인 로그 조회
	 * @since 2024-06-09
	 */
    public List<LogLoginDTO> findAllByUsername(String username) {
    	return logRepository.findAllByUsername(username);
    }

	/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
    public List<LogLoginDTO> findAll() {
    	return logRepository.findAll();
    }

		/***
	 * @author 140024
	 * @implNote logRepository 객체의 findLoginLogAll 호출하여 LogLoginEntity 리스트 반환
	 * @since 2024-06-09
	 */
    public void insert(final LogLoginDTO logLoginDTO) {
    	logRepository.insert(logLoginDTO);
    }

}