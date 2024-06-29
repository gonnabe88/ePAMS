package epams.com.board.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.board.entity.BoardImageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 게시판이미지 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardImageDTO {
    /***
     * @author 140024
     * @implNote 자동으로 생성되는 auto increment number
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote 원본 이미지 이름
     * @since 2024-06-09
     */
    private String originalFileName;

    /***
     * @author 140024
     * @implNote 본문
     * @implSpec 저장된 이미지 이름
     * @since 2024-06-09
     */
    private String storedFileName;

    /***
     * @author 140024
     * @implNote 이미지가 첨부된 게시판 ID (작업 예정)
     * @since 2024-06-09
     */
    private Long boardId;

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
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static BoardImageDTO toDTO(BoardImageEntity boardImageEntity) {
        final BoardImageDTO boardImageDTO = new BoardImageDTO();
        boardImageDTO.setSeqId(boardImageEntity.getBLB_IMG_SNO());
        boardImageDTO.setOriginalFileName(boardImageEntity.getORC_FL_NM());
        boardImageDTO.setStoredFileName(boardImageEntity.getSVR_FL_NM());
        boardImageDTO.setBoardId(boardImageEntity.getBoardEntity().getBLB_SNO());
        return boardImageDTO;
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public BoardImageEntity toEntity() {
        BoardImageEntity boardImageEntity = new BoardImageEntity();
        boardImageEntity.setBLB_IMG_SNO(this.seqId);
        boardImageEntity.setORC_FL_NM(this.originalFileName);
        boardImageEntity.setSVR_FL_NM(this.storedFileName);
        boardImageEntity.setGNT_DTM(this.createdTime);
        boardImageEntity.setAMN_DTM(this.updatedTime);
        return boardImageEntity;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardImageDTO> toDTOList(List<BoardImageEntity> boardImageEntities) {
        return boardImageEntities.stream().map(BoardImageDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<BoardImageEntity> toEntityList(List<BoardImageDTO> boardImageDTOs) {
        return boardImageDTOs.stream().map(BoardImageDTO::toEntity).collect(Collectors.toList());
    }
}
