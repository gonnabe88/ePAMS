package epams.domain.com.holiday;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

/***
 * @author 140024
 * @implNote 상수값 정보를 DBMS에서 가져오기 위한 레파지토리 
 * @since 2024-06-14
 */
@Repository
@RequiredArgsConstructor
public class HolidayRepository {

	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
    private final SqlSessionTemplate sql;
    
	/***
	 * @author 140024
	 * @implNote 휴일 목록 조회
	 * @since 2024-06-09
	 */
	public List<String> findholiYmd() {
		return sql.selectList("Holiday.findholiYmd");
	}

}