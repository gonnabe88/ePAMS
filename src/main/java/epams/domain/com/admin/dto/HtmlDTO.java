package epams.domain.com.admin.dto;

import epams.model.vo.HtmlVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 코드 테이블 데이터 정의 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true) // 상위 클래스의 toString 포함
public class HtmlDTO extends HtmlVO {

}