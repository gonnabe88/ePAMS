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
public class BoardDTO extends BaseDTO{
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
        super();
        this.seqId = seqId;
        this.boardTitle = boardTitle;
        this.boardContents = new String(boardContents, StandardCharsets.UTF_8);
        this.boardWriter = boardWriter;
        this.category = category;
        this.boardHits = boardHits;
        this.fileAttached = 0;
        this.setCreatedTime(boardCreatedTime);
    }
}