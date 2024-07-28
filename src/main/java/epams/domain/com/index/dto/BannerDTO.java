package epams.domain.com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannerDTO {

    /***
     * @author 140024
     * @implNote 제목
     * @since 2024-06-09
     */
    private String title;

    /***
     * @author 140024
     * @implNote 부제목1
     * @since 2024-06-09
     */
    private String subtitle1;

    /***
     * @author 140024
     * @implNote 부제목2
     * @since 2024-06-09
     */
    private String subtitle2;

    /***
     * @author 140024
     * @implNote 버튼
     * @since 2024-06-09
     */
    private String button;
}
