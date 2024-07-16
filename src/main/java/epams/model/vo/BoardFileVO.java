package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote 게시판 첨부파일 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardFileVO extends BaseVO {
	
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
    private BoardVO boardVO;
    
    /***
     * @author 140024
     * @implNote 저장경로
     * @since 2024-06-09
     */
    private String storedPath;

}