package epams.domain.com.board.dto;

import epams.model.vo.BoardFileVO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 게시판 첨부파일 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardFileDTO extends BoardFileVO {

    /***
     * @author 140024
     * @implNote 게시글 (외래키 설정 BOARD_FILE.BOARD_ID - BOARD.SEQ_ID)
     * @since 2024-06-10
     */
    private BoardDTO boardDTO;

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