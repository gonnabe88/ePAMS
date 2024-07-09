package epams.com.admin.repository;

import epams.com.admin.dto.CountByCategoryDTO;
import epams.com.admin.dto.CountByDateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @author 140024
 * @implNote 각종 로그 데이터 관리를 위한 repository
 * @since 2024-06-09
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DashboardRepository {
	/***
	 * @author 140024
	 * @implNote SQL 쿼리 실행은 위한 SqlSessionTemplate
	 * @since 2024-06-09
	 */
    private final SqlSessionTemplate sql;

	/**
	 * @author K140024
	 * @implNote 차트 생성용 일자별 건수 조회
	 * @since 2024-04-26
	 */
	public List<CountByDateDTO> getLoginCountsByDate() {
		return sql.selectList("Dashboard.findLoginCountsByDate");
	}

	/**
	 * @author K140024
	 * @implNote 차트 생성용 일자별 건수 조회
	 * @since 2024-04-26
	 */
	public List<CountByDateDTO> getViewCountsByDate() {
		return sql.selectList("Dashboard.findViewCountsByDate");
	}

	/**
	 * @author K140024
	 * @implNote 차트 생성용 일자별 건수 조회
	 * @since 2024-04-26
	 */
	public List<CountByCategoryDTO> getLoginTypeCountByCategory() {
		return sql.selectList("Dashboard.findLoginTypeCountByCategory");
	}
    
}
