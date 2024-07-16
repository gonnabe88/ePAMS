package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote 게시판이미지 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardImageVO extends BaseVO {
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

}
