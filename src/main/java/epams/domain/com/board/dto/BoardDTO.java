package epams.domain.com.board.dto;

import epams.model.vo.BoardVO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/***
 * @author 140024
 * @implNote 게시판 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO extends BoardVO {

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
        this.setSeqId(seqId);
        this.setBoardWriter(boardWriter);
        this.setBoardContents(new String(boardContents, StandardCharsets.UTF_8));
        this.setBoardTitle(boardTitle);
        this.setCategory(category);
        this.setBoardHits(boardHits);
        this.setFileAttached("0");
        this.setCreatedTime(boardCreatedTime);
    }
}