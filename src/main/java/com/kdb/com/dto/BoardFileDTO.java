package com.kdb.com.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.kdb.com.entity.BoardEntity;
import com.kdb.com.entity.BoardFileEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 게시판 첨부파일 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardFileDTO {
	
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    private Long seqId;
    /***
     * @author 140024
     * @implNote 파일명
     * @since 2024-06-10
     */
    private String originalFileName;
    
    /***
     * @author 140024
     * @implNote 저장파일명 (중복방지)
     * @since 2024-06-10
     */
    private String storedFileName;
    
    /***
     * @author 140024
     * @implNote 게시글 ID (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    private Long boardId;
    
    /***
     * @author 140024
     * @implNote 게시글 (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    private BoardDTO boardDTO;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-09
     */
    private String storedPath;
    
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
     * @implNote 주어진 파라미터를 사용하여 BoardFileDTO 객체를 생성하고 반환하는 정적 팩토리 메소드
     * @since 2024-06-10
     */
    public static BoardFileDTO toBoardFileDTO(final BoardDTO boardDTO, final String originalFileName, final String storedFileName, final String storedPath) {
        final BoardFileDTO boardFileDTO = new BoardFileDTO();
        boardFileDTO.setOriginalFileName(originalFileName);
        boardFileDTO.setStoredFileName(storedFileName);
        boardFileDTO.setBoardId(boardDTO.getSeqId());
        boardFileDTO.setStoredPath(storedPath);
        boardFileDTO.setBoardDTO(boardDTO);
        return boardFileDTO;
    }
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static BoardFileDTO toDTO(BoardFileEntity boardFileEntity) {
        BoardFileDTO boardFileDTO = new BoardFileDTO();
        boardFileDTO.setSeqId(boardFileEntity.getSEQ_ID());
        boardFileDTO.setOriginalFileName(boardFileEntity.getORIGINAL_FILENAME());
        boardFileDTO.setStoredFileName(boardFileEntity.getSTORED_FILENAME());
        boardFileDTO.setStoredPath(boardFileEntity.getSTORED_PATH());
        boardFileDTO.setBoardId(boardFileEntity.getBoardEntity().getSEQ_ID());
        boardFileDTO.setCreatedTime(boardFileEntity.getCREATED_TIME());
        boardFileDTO.setUpdatedTime(boardFileEntity.getUPDATED_TIME());
        return boardFileDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public BoardFileEntity toEntity() {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setSEQ_ID(this.seqId);
        boardFileEntity.setORIGINAL_FILENAME(this.originalFileName);
        boardFileEntity.setSTORED_FILENAME(this.storedFileName);
        boardFileEntity.setSTORED_PATH(this.storedPath);
        if (this.boardId != null) {
            BoardEntity boardEntity = new BoardEntity();
            boardEntity.setSEQ_ID(this.boardId);
            boardFileEntity.setBoardEntity(boardEntity);
        }
        boardFileEntity.setCREATED_TIME(this.createdTime);
        boardFileEntity.setUPDATED_TIME(this.updatedTime);
        return boardFileEntity;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardFileDTO> toDTOList(List<BoardFileEntity> boardFileEntities) {
        return boardFileEntities.stream().map(BoardFileDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardFileEntity> toEntityList(List<BoardFileDTO> boardFileDTOs) {
        return boardFileDTOs.stream().map(BoardFileDTO::toEntity).collect(Collectors.toList());
    }
    
}
