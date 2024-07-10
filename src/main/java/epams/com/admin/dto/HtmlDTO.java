package epams.com.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.admin.entity.HtmlEntity;
import epams.com.board.dto.BaseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class HtmlDTO extends BaseDTO {

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