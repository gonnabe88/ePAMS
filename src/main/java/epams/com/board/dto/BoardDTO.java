package epams.com.board.dto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import epams.com.board.entity.BoardEntity;
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
    private LocalDate createdTime;
    
    
    /***
     * @author 140024
     * @implNote 수정시간
     * @since 2024-06-09
     */
    private LocalDate updatedTime;

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
     * @implNote 카테고리
     * @since 2024-06-09
     */
    private String category;
    
    /***
     * @author 140024
     * @implNote 테스트
     * @since 2024-06-09
     */
    private String test;    
    
    /***
     * @author 140024
     * @implNote 특정 필드들을 매개변수로 하는 생성자
     * @since 2024-06-09
     */
    public BoardDTO(final Long seqId, final String boardWriter, final byte[] boardContents, final String boardTitle, final String category, final int boardHits, final LocalDate boardCreatedTime) {
        this.seqId = seqId;
        this.boardWriter = boardWriter;
        this.boardContents = new String(boardContents, StandardCharsets.UTF_8);
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.createdTime = boardCreatedTime;
        this.category = category;
    }   
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static BoardDTO toDTO(final BoardEntity boardEntity) {
    	final BoardDTO boardDTO = new BoardDTO();
        if(boardEntity != null) {
            boardDTO.setSeqId(boardEntity.getBLB_SNO());
            boardDTO.setBoardWriter(boardEntity.getDUPR_ENO());
            boardDTO.setBoardTitle(boardEntity.getBLB_TTL_CONE());
            boardDTO.setBoardContents(boardEntity.getBLB_CONE());
            boardDTO.setCategory(boardEntity.getCTG_NM());
            boardDTO.setBoardHits(boardEntity.getNAC_INQ_NBR());
            boardDTO.setCreatedTime(boardEntity.getGNT_DTM());
            boardDTO.setUpdatedTime(boardEntity.getAMN_DTM());
            boardDTO.setFileAttached(boardEntity.getFL_APG_YN()); 
        }        
        return boardDTO;
    }
    
    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public BoardEntity toEntity() {
        final BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBLB_SNO(this.seqId);
        boardEntity.setDUPR_ENO(this.boardWriter);
        boardEntity.setBLB_TTL_CONE(this.boardTitle);
        boardEntity.setBLB_CONE(this.boardContents);
        boardEntity.setFL_APG_YN(this.fileAttached);
        boardEntity.setCTG_NM(this.category);
        boardEntity.setNAC_INQ_NBR(this.boardHits);
        boardEntity.setAMN_DTM(this.updatedTime);
        boardEntity.setGNT_DTM(this.createdTime);
        return boardEntity;
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardDTO> toDTOList(List<BoardEntity> boardEntities) {
        return boardEntities.stream()
                .map(BoardDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardEntity> toEntityList(List<BoardDTO> boardDTOs) {
        return boardDTOs.stream()
                .map(BoardDTO::toEntity)
                .collect(Collectors.toList());
    }
    
}