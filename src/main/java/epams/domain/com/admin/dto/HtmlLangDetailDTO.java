package epams.domain.com.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote (JOIN) 데이터 정의 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class HtmlLangDetailDTO {
	
    /***
     * @author 140024
     * @implNote HTML 경로
     * @since 2024-06-09
     */
    private String html;
    
    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    private String htmlName;
    
    /***
     * @author 140024
     * @implNote 공통코드
     * @since 2024-06-09
     */
    private String lang;

    /***
     * @author 140024
     * @implNote 코드명
     * @since 2024-06-09
     */
    private String langName;
    
}