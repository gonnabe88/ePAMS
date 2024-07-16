package epams.model.vo;

import lombok.*;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HtmlVO extends BaseVO {

    /***
     * @author 140024
     * @implNote HTML파일경로(HTML_FL_PTH)
     * @since 2024-06-09
     */
    private String html;

    /***
     * @author 140024
     * @implNote HTML파일명(HTML_FL_NM)
     * @since 2024-06-09
     */
    private String htmlName;

}