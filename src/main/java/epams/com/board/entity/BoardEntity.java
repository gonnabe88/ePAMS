package epams.com.board.entity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import epams.com.board.dto.BoardDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 게시판 테이블 정의 entity
 * @since 2024-06-09
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "com_board")
public class BoardEntity extends BaseEntity {
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column
    private Long SEQ_ID;
    
    /***
     * @author 140024
     * @implNote 제목
     * @since 2024-06-09
     */
    @Column(nullable = false)
    private String BOARD_TITLE;
    
    /***
     * @author 140024
     * @implNote 본문
     * @implSpec Blob 타입으로 선언
     * @since 2024-06-09
     */
    @Lob
    @Column(nullable = false)
    private byte[] BOARD_CONTENTS;

    /***
     * @author 140024
     * @implNote 작성자
     * @since 2024-06-09
     */
    @Column(length = 255, nullable = false) 
    private String BOARD_WRITER;

    /***
     * @author 140024
     * @implNote 카테고리 (IT, 인사 등)
     * @since 2024-06-09
     */
    @Column(length = 16, nullable = false)
    private String CATEGORY;

    /***
     * @author 140024
     * @implNote 조회수
     * @since 2024-06-09
     */
    @Column
    private int BOARD_HITS;

    /***
     * @author 140024
     * @implNote 파일첨부 여부 (첨부 1, 미첨부 0)
     * @since 2024-06-09
     */
    @Column
    private int FILE_ATTACHED; // 1 or 0

    /***
     * @author 140024
     * @implNote (JPA) 하나의 게시글과 다수의 파일을 매핑
     * @since 2024-06-09
     */
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityL = new ArrayList<>();

//    /***
//     * @author 140024
//     * @implNote (static factory method) 게시글 저장을 위한 엔티티 생성  
//     * @param boardDTO 게시글 정보를 담고 있는 DTO
//     * @return BoardEntity 생성된 게시글 엔티티
//     * @since 2024-06-09
//     */
//    public static BoardEntity toSaveEntity(final BoardDTO boardDTO) {
//        final BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents().getBytes(StandardCharsets.UTF_8));
//        boardEntity.setCategory(boardDTO.getCategory());
//        boardEntity.setBoardHits(0);
//        boardEntity.setFileAttached(0); // 파일 없음.
//        return boardEntity;
//    }

//    /***
//     * @author 140024
//     * @implNote (static factory method) 게시글 수정을 위한 엔티티 생성
//     * @param boardDTO 게시글 정보를 담고 있는 DTO
//     * @return BoardEntity 수정된 게시글 엔티티
//     * @since 2024-06-09
//     */
//    public static BoardEntity toUpdateEntity(final BoardDTO boardDTO) {
//        final BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setSeqId(boardDTO.getSeqId());
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents().getBytes(StandardCharsets.UTF_8));
//        boardEntity.setCategory(boardDTO.getCategory());
//        boardEntity.setBoardHits(boardDTO.getBoardHits());
//        return boardEntity;
//    }
//
//    /***
//     * @author 140024
//     * @implNote (static factory method) 파일이 있는 게시글 저장을 위한 엔티티 생성
//     * @param boardDTO 게시글 정보를 담고 있는 DTO
//     * @return BoardEntity 생성된 게시글 엔티티
//     * @since 2024-06-09
//     */
//    public static BoardEntity toSaveFileEntity(final BoardDTO boardDTO) {
//        final BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents().getBytes(StandardCharsets.UTF_8));
//        boardEntity.setCategory(boardDTO.getCategory());
//        boardEntity.setBoardHits(0);
//        boardEntity.setFileAttached(1); // 파일 있음.
//        return boardEntity;
//    }
//    
//    /***
//     * @author 140024
//     * @implNote (static factory method) 파일이 있는 게시글 수정을 위한 엔티티 생성
//     * @param boardDTO 게시글 정보를 담고 있는 DTO
//     * @return BoardEntity 수정된 게시글 엔티티
//     * @since 2024-06-09
//     */
//    public static BoardEntity toUpdateFileEntity(final BoardDTO boardDTO) {
//        final BoardEntity boardEntity = new BoardEntity();
//        boardEntity.setSeqId(boardDTO.getSeqId());
//        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
//        boardEntity.setBoardContents(boardDTO.getBoardContents().getBytes(StandardCharsets.UTF_8));
//        boardEntity.setCategory(boardDTO.getCategory());
//        boardEntity.setBoardHits(boardDTO.getBoardHits());
//        boardEntity.setFileAttached(1); // 파일 있음.
//        return boardEntity;
//    }
}