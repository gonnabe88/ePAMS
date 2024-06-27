package epams.com.board.entity;
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
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-10
     */
    @Id
    @Column(name = "BOARD_FILE_SNO")
    @Comment("게시판첨부파일일련번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SEQ_ID;

    /***
     * @author 140024
     * @implNote 파일명
     * @since 2024-06-10
     */
    @Column(name = "ORIGINAL_FILENAME")
    @Comment("원본파일명")
    private String ORIGINAL_FILENAME;

    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    @Column(name = "STORED_FILENAME")
    @Comment("저장파일명")
    private String STORED_FILENAME;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-10
     */
    @Column(name = "STORED_PATH")
    @Comment("저장경로")
    private String STORED_PATH;

    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_SNO", foreignKey = @ForeignKey(name = "FK_BOARD_FILE_SNO_BOARD_SNO", foreignKeyDefinition = "FOREIGN KEY (BOARD_SNO) REFERENCES THURXE_CBRDMM (BOARD_SNO) ON DELETE CASCADE"))
    private BoardEntity boardEntity;
    
    /***
     * @author 140024
     * @implNote lombok getter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public Long getBOARD_ID() {
        return boardEntity != null ? boardEntity.getSEQ_ID() : null;
    }
    
    /***
     * @author 140024
     * @implNote lombok setter에서 자동으로 인식하지 못하는 문제로 별도 추가
     * @since 2024-06-10
     */
    public void setBOARD_ID(final Long boardId) {
        if (this.boardEntity == null) {
            this.boardEntity = new BoardEntity();
        }
        this.boardEntity.setSEQ_ID(boardId);
    }

}