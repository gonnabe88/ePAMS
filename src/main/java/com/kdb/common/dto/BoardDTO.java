package com.kdb.common.dto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kdb.common.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 게시판 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    private Long seqId;
    
    /***
     * @author 140024
     * @implNote 작성자
     * @since 2024-06-09
     */
    private String boardWriter;
    
    /***
     * @author 140024
     * @implNote 제목
     * @since 2024-06-09
     */
    private String boardTitle;
    
    /***
     * @author 140024
     * @implNote 본문
     * @implSpec String 타입으로 선언 후 Entity byte[] <> String 변환하여 사용
     * @since 2024-06-09
     */
    private String boardContents;
    
    /***
     * @author 140024
     * @implNote 조회수
     * @since 2024-06-09
     */
    private int boardHits;
    
    
    /***
     * @author 140024
     * @implNote 생성시간
     * @since 2024-06-09
     */
    private LocalDateTime createdTime;
    
    
    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    private LocalDateTime updatedTime;

    /***
     * @author 140024
     * @implNote 첨부파일 리스트
     * @since 2024-06-09
     */
    private List<MultipartFile> boardFile; // save.html -> Controller 파일 담는 용도
    
    /***
     * @author 140024
     * @implNote 첨부파일명(사용자 원본)
     * @since 2024-06-09
     */
    private String originalFileName; // 원본 파일 이름
    
    /***
     * @author 140024
     * @implNote 첨부파일명(서버 저장)
     * @since 2024-06-09
     */
    private String storedFileName; // 서버 저장용 파일 이름
    
    /***
     * @author 140024
     * @implNote 첨부파일 보유여부
     * @since 2024-06-09
     */
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)
    
    /***
     * @author 140024
     * @implNote 새글 여부
     * @since 2024-06-09
     */
    private boolean isNew;
    
    /***
     * @author 140024
     * @implNote 카테고리
     * @since 2024-06-09
     */
    private String category;

    /***
     * @author 140024
     * @implNote 특정 필드들을 매개변수로 하는 생성자
     * @since 2024-06-09
     */
    public BoardDTO(final Long seqId, final String boardWriter, final byte[] boardContents, final String boardTitle, final String category, final int boardHits, final LocalDateTime boardCreatedTime) {
        this.seqId = seqId;
        this.boardWriter = boardWriter;
        this.boardContents = new String(boardContents, StandardCharsets.UTF_8);
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.createdTime = boardCreatedTime;
        this.isNew = isNewBoard(boardCreatedTime);
        this.category = category;
    }   
    
    /***
     * @author 140024
     * @implNote 엔티티를 DTO로 변환하는 정적 메소드
     * @since 2024-06-09
     */
    public static BoardDTO toBoardDTO(final BoardEntity boardEntity) {
    	final BoardDTO boardDTO = new BoardDTO();
        boardDTO.setSeqId(boardEntity.getSeqId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(new String(boardEntity.getBoardContents(), StandardCharsets.UTF_8));
        boardDTO.setCategory(boardEntity.getCategory());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setUpdatedTime(boardEntity.getUpdatedTime());
        boardDTO.setNew(isNewBoard(boardEntity.getCreatedTime()));
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
        }

        return boardDTO;
    }
    
    /***
     * @author 140024
     * @implNote 최근 7일 이내에 등록된 게시글인지 확인하는 메소드
     * @return  최근 7일 이내에 등록된 게시글이면 true, 아니면 false
     * @since 2024-06-09
     */
    private static boolean isNewBoard(final LocalDateTime boardCreatedTime) {
        return boardCreatedTime != null && ChronoUnit.DAYS.between(boardCreatedTime, LocalDateTime.now()) <= 7;
    }
    
    public BoardEntity toEntity() {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setSeqId(this.seqId);
        boardEntity.setBoardWriter(this.boardWriter);
        boardEntity.setBoardTitle(this.boardTitle);
        boardEntity.setBoardContents(this.boardContents.getBytes(StandardCharsets.UTF_8));
        boardEntity.setCategory(this.category);
        boardEntity.setBoardHits(this.boardHits);
        return boardEntity;
    }
    
}