package epams.domain.com.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
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