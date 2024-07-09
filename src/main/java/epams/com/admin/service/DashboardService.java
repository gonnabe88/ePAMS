package epams.com.admin.service;

import epams.com.admin.dto.CountByCategoryDTO;
import epams.com.admin.dto.CountByDateDTO;
import epams.com.admin.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리 로직 처리를 위한 service
 * @since 2024-06-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {
	
	/***
	 * @author 140024
	 * @implNote logRepository 객체 생성
	 * @since 2024-06-09
	 */
	private final DashboardRepository dashboardRepository;

	/***
	 * @author 140024
	 * @implNote dashboardRepository 객체의 getLoginCountsByDate 호출하여 CountByDateDTO 리스트 반환
	 * @since 2024-06-09
	 */
	public List<CountByDateDTO> getLoginCountsByDate() {
		return dashboardRepository.getLoginCountsByDate();
	}

	/***
	 * @author 140024
	 * @implNote dashboardRepository 객체의 getViewCountsByDate 호출하여 CountByDateDTO 리스트 반환
	 * @since 2024-06-09
	 */
	public List<CountByDateDTO> getViewCountsByDate() {
		return dashboardRepository.getViewCountsByDate();
	}

	/***
	 * @author 140024
	 * @implNote dashboardRepository 객체의 getLoginTypeCountByCategory 호출하여 CountByDateDTO 리스트 반환
	 * @since 2024-06-09
	 */
	public List<CountByCategoryDTO> getLoginTypeCountByCategory() {
		return dashboardRepository.getLoginTypeCountByCategory();
	}
}