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
 * @implNote 게시판 이미지 Table을 객체로 관리하기 위한 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "COM_BOARD_IMAGE")
public class BoardImageEntity extends BaseEntity {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long SEQ_ID;

    /***
     * @author 140024
     * @implNote 원본 파일명
     * @since 2024-06-09
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
     * @implNote 게시글 ID (외래키 설정 BOARD_IMAGE.BOARDID - BOARD.SEQID)
     * @since 2024-06-10
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
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