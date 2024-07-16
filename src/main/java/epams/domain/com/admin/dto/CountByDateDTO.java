package epams.domain.com.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CountByDateDTO {
	
    /***
     * @author 140024
     * @implNote 날짜
     * @since 2024-07-08
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    /***
     * @author 140024
     * @implNote 성공 횟수
     * @since 2024-07-08
     */
    private Long successCount;

    /***
     * @author 140024
     * @implNote 실패 횟수
     * @since 2024-07-08
     */
    private Long failureCount;

    /***
     * @author 140024
     * @implNote 전체 횟수
     * @since 2024-07-08
     */
    private Long totalCount;
    
}