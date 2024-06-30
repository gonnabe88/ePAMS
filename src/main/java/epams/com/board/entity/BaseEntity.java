package epams.com.board.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	 * @implNote 수정일시
	 * @since 2024-06-09
	 */
    @UpdateTimestamp
    @Column(name = "AMN_DTM", insertable = false, columnDefinition = "DATE")
    @Comment("수정일시")
    private LocalDate AMN_DTM;
	
	/***
	 * @author 140024
	 * @implNote 생성일시
	 * @since 2024-06-09
	 */
    @CreationTimestamp
    @Column(name = "GNT_DTM", updatable = false, columnDefinition = "DATE")
    @Comment("생성일시")
    private LocalDate GNT_DTM;
}