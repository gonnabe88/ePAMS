package epams.com.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.board.entity.BoardEntity;
import epams.com.board.entity.BoardFileEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class BoardFileDTO extends BaseDTO {
	
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
}