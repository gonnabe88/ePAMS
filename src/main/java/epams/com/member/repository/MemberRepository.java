package epams.com.member.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import epams.com.member.dto.MemberDTO;
import epams.com.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	/***
	 * @author 140024
	 * @implNote MyBatis DBMS 연결을 위한 템플릿 클래스
	 * @since 2024-06-10
	 */
	private final SqlSessionTemplate sql;

	/***
	 * @author 140024
	 * @implNote 모든 내용 조회
	 * @since 2024-06-09
	 */
	public List<MemberDTO> findAll() {
		final List<MemberEntity> memberEntities = sql.selectList("Member.findAll");
		return MemberDTO.toDTOs(memberEntities);
	}

	/***
	 * @author 140024
	 * @implNote 기본키 검색
	 * @since 2024-06-09
	 */
	public Long countById(final MemberDTO memberDTO) {
		return sql.selectOne("Code.countById", memberDTO.toEntity());
	}

	/***
	 * @author 140024
	 * @implNote 신규 데이터 입력
	 * @since 2024-06-09
	 */
	public void insert(final MemberDTO memberDTO) {
		sql.insert("Code.insert", memberDTO.toEntity());
	}

	/***
	 * @author 140024
	 * @implNote 기존 데이터 삭제
	 * @since 2024-06-09
	 */
	public void delete(final MemberDTO memberDTO) {
		sql.delete("Code.delete", memberDTO.toEntity());
	}

	/***
	 * @author 140024
	 * @implNote 데이터 업데이트(기본키 제외)
	 * @since 2024-06-09
	 */
	public void update(final MemberDTO memberDTO) {
		sql.update("Code.update", memberDTO.toEntity());
	}

}