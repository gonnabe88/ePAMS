package epams.com.board.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "com_board_file")
@NoArgsConstructor // 기본생성자
public class BoardFileEntity extends BaseEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-10
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SEQ_ID;

    /***
     * @author 140024
     * @implNote 파일명
     * @since 2024-06-10
     */
    @Column
    private String ORIGINAL_FILENAME;

    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    @Column
    private String STORED_FILENAME;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-10
     */
    @Column
    private String STORED_PATH;

    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private BoardEntity boardEntity;
    
    // 추가된 getter 메서드
    public Long getBOARD_ID() {
        return boardEntity != null ? boardEntity.getSEQ_ID() : null;
    }
    // 추가된 setter 메서드
    public void setBOARD_ID(Long boardId) {
        if (this.boardEntity == null) {
            this.boardEntity = new BoardEntity();
        }
        this.boardEntity.setSEQ_ID(boardId);
    }

}