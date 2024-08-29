package epams.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "THURXE_CBRDMM")
@Comment("인사_외부근태 공지사항게시판메인기본")
public class BoardEntity extends BaseEntity {
    
    /**
     * @author 140024
     * @implNote BLB_SNO 시퀀스 이름 상수
     * @since 2024-06-09
     */
    private static final String SEQUENCE = "SQ_THURXE_CBRDMM_1";

    /**
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id 
    @Column(name = "BLB_SNO", columnDefinition = "NUMBER(22)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    @Comment("게시판일련번호")
    private Long BLB_SNO;
    
    /**
     * @author 140024
     * @implNote 제목
     * @since 2024-06-09
     */
    @Column(name = "BLB_NM", length = 300, nullable = false)
    @Comment("게시판명")
    private String BLB_NM;
    
    /**
     * @author 140024
     * @implNote 본문
     * @implSpec Blob 타입으로 선언
     * @since 2024-06-09
     */
    @Column(name = "BLB_CONE", length = 4000, nullable = false)
    @Comment("게시판내용")
    private String BLB_CONE;

    /**
     * @author 140024
     * @implNote 작성자
     * @since 2024-06-09
     */
    @Column(name = "DUPR_ENO",  length = 32, nullable = false)
    @Comment("작성자사원번호")
    private String DUPR_ENO;

    /**
     * @author 140024
     * @implNote 카테고리 (IT, 인사 등)
     * @since 2024-06-09
     */
    @Column(name = "CTG_NM", length = 60, nullable = false)
    @Comment("카테고리명")
    private String CTG_NM;

    /**
     * @author 140024
     * @implNote 조회수
     * @since 2024-06-09
     */
    @Column(name = "NAC_INQ_NBR", columnDefinition = "NUMBER(10)")
    @Comment("게시물조회수")
    private int NAC_INQ_NBR;

    /**
     * @author 140024
     * @implNote 파일첨부여부 (첨부 1, 미첨부 0)
     * @since 2024-06-09
     */
    @Column(name = "FL_APG_YN", length = 1)
    @Comment("파일첨부여부")
    private String FL_APG_YN;

    /**
     * @author 140024
     * @implNote (JPA) 하나의 게시판과 다수의 파일을 매핑
     * @since 2024-06-09
     */
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityL = new ArrayList<>();
}
