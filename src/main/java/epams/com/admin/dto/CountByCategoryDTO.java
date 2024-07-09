package epams.com.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class CountByCategoryDTO {

    /***
     * @author 140024
     * @implNote 성공 횟수
     * @since 2024-07-08
     */
    private String category;

    /***
     * @author 140024
     * @implNote 전체 횟수
     * @since 2024-07-08
     */
    private Long totalCount;
    
}