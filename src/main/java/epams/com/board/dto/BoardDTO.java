package epams.com.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.board.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * @implNote [Column] 게시판일련번호(BLB_SNO)
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote [Column] 게시판제목(BLB_TTL_CONE)
     * @since 2024-06-09
     */
    private String boardTitle;

    /***
     * @author 140024
     * @implNote [Column] 게시판내용(BLB_CONE)
     * @implSpec ByteArray를 String으로 변환하여 저장
     * @since 2024-06-09
     */
    private String boardContents;

    /***
     * @author 140024
     * @implNote [Column] 작성자사원번호(DUPR_ENO)
     * @since 2024-06-09
     */
    private String boardWriter;

    /***
     * @author 140024
     * @implNote [Column] 카테고리명(CTG_NM)
     * @since 2024-06-09
     */
    private String category;

    /***
     * @author 140024
     * @implNote [Column] 게시물조회수(NAC_INQ_NBR)
     * @since 2024-06-09
     */
    private int boardHits;

    /***
     * @author 140024
     * @implNote [Column] 파일첨부여부(FL_APG_YN)
     * @since 2024-06-09
     */
    private int fileAttached; 

    /***
     * @author 140024
     * @implNote [Column] 수정일시(AMN_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    /***
     * @author 140024
     * @implNote [Column] 생성일시(GNT_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /***
     * @author 140024
     * @implNote 첨부파일 리스트
     * @implSpec 첨부파일 바이너리를 MultipartFile 형식으로 저장
     * @since 2024-06-09
     */
    private List<MultipartFile> boardFile; 

    /***
     * @author 140024
     * @implNote 특정 필드들을 매개변수로 하는 생성자
     * @since 2024-06-09
     */
    public BoardDTO(final Long seqId, final String boardWriter, final byte[] boardContents, final String boardTitle,
            final String category, final int boardHits, final LocalDateTime boardCreatedTime) {
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
        if (boardEntity != null) {
            boardDTO.setSeqId(boardEntity.getBLB_SNO());
            boardDTO.setBoardWriter(boardEntity.getDUPR_ENO());
            boardDTO.setBoardTitle(boardEntity.getBLB_NM());
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
        boardEntity.setBLB_NM(this.boardTitle);
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
    public static List<BoardDTO> toDTOList(final List<BoardEntity> boardEntities) {
        return boardEntities.stream()
                .map(BoardDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardEntity> toEntityList(final List<BoardDTO> boardDTOs) {
        return boardDTOs.stream()
                .map(BoardDTO::toEntity)
                .collect(Collectors.toList());
    }

}