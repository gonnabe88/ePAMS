package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote 게시판 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVO extends BaseVO {
    /***
     * @author 140024
     * @implNote [Column] 게시판일련번호(BLB_SNO)
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote [Column] 게시판타입(BLB_CLSF_C)
     * @since 2024-06-09
     */
    private String boardType = "10";

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
    private String fileAttached;
}