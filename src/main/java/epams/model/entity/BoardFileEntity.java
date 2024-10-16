package epams.model.entity;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 게시판 첨부파일 게시판 정의 entity
 * @since 2024-06-09
 */
@Entity
@Getter
@Setter
@NoArgsConstructor // 기본생성자
@Table(name = "THURXE_CBRDFM")
@Comment("인사_외부근태 공지사항게시판파일기본")
public class BoardFileEntity extends BaseEntity {
	
    /**
     * @author 140024
     * @implNote 시퀀스 이름 상수
     * @since 2024-06-09
     */
    private static final String SEQUENCE = "SQ_THURXE_CBRDFM_1";
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-10
     */
    @Id
    @Column(name = "BLB_APG_FL_SNO", columnDefinition = "NUMBER(22)")
    @Comment("게시판첨부파일일련번호")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 1)
    private Long BLB_APG_FL_SNO;

    /***
     * @author 140024
     * @implNote 파일명
     * @since 2024-06-10
     */
    @Column(name = "ORC_FL_NM")
    @Comment("원본파일명")
    private String ORC_FL_NM;

    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    @Column(name = "SVR_FL_NM")
    @Comment("서버파일명")
    private String SVR_FL_NM;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-10
     */
    @Column(name = "FL_KPN_PTH")
    @Comment("파일저장경로")
    private String FL_KPN_PTH;

    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_FILE.BOARD_SNO - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLB_SNO", foreignKey = @ForeignKey(name = "FK_THURXE_CBRDFM_BLB_SNO", foreignKeyDefinition = "FOREIGN KEY (BLB_SNO) REFERENCES OEHR.THURXE_CBRDMM (BLB_SNO) ON DELETE CASCADE"))
    @Comment("게시판일련번호")
    private BoardEntity boardEntity;
    
    /***
     * @author 140024
     * @implNote lombok getter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public Long getBLB_SNO() {
        return boardEntity != null ? boardEntity.getBLB_SNO() : null;
    }
    
    /***
     * @author 140024
     * @implNote lombok setter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public void setBLB_SNO(final Long boardId) {
        if (this.boardEntity == null) {
            this.boardEntity = new BoardEntity();
        }
        this.boardEntity.setBLB_SNO(boardId);
    }
}