package epams.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/***
 * @author 140024
 * @implNote 모든 테이블이 공통으로 가져야할 컬럼을 정의하기 위한 entity
 * @since 2024-06-09
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class BaseEntity {

	/***
	 * @author 140024
	 * @implNote 삭제여부(시스템 공통 컬럼)
	 * @since 2024-06-09
	 */
	@Column(name = "DEL_YN", length = 1, nullable = false)
	@Comment("삭제여부")
	private String DEL_YN = "N";

	/***
	 * @author 140024
	 * @implNote GUID 거래ID(시스템 공통 컬럼 - Nexcore)
	 * @since 2024-06-09
	 */
	@Column(name = "GUID", length = 38, nullable = false)
	@Comment("GUID")
	private String GUID = new String(new char[38]).replace('\0', '0');

	/***
	 * @author 140024
	 * @implNote GUID 거래ID 진행 일련번호(시스템 공통 컬럼 - Nexcore)
	 * @since 2024-06-09
	 */
	@Column(name = "GUID_PRG_SNO", columnDefinition = "NUMBER(4)", nullable = false)
	@Comment("GUID진행일련번호")
	private int GUID_PRG_SNO = 0;

	/***
	 * @author 140024
	 * @implNote 최종변경사용자ID(시스템 공통 컬럼)
	 * @since 2024-06-09
	 */
	@Column(name = "LST_CHG_USID", length = 14, nullable = false)
	@Comment("최종변경사용자ID")
	private String LST_CHG_USID = "SYSTEM";

	/***
	 * @author 140024
	 * @implNote 수정일시
	 * @since 2024-06-09
	 */
    @UpdateTimestamp
    @Column(name = "LST_CHG_DTM", columnDefinition = "DATE")
    @Comment("최종변경일시")
    private LocalDateTime LST_CHG_DTM;
	
	/***
	 * @author 140024
	 * @implNote 생성일시
	 * @since 2024-06-09
	 */
    @CreationTimestamp
    @Column(name = "FST_ENT_DTM", columnDefinition = "DATE")
    @Comment("생성일시")
    private LocalDateTime FST_ENT_DTM;

	/***
	 * @author 140024
	 * @implNote 생성일시
	 * @since 2024-06-09
	 */
	@Column(name = "FST_ENT_USID", length = 14, nullable = false)
	@Comment("생성사용자ID")
	private String FST_ENT_USID = "SYSTEM";
}