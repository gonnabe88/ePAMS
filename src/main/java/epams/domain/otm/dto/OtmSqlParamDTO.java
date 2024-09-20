package epams.domain.otm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 140024
 * @implNote 년간근태조회  DTO
 * @since 2024-06-09
 */
@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtmSqlParamDTO {

    /***
     * @author 140024
     * @implNote 컬럼구분
     * @since 2024-09-11
     */
    private String flag;

    /***
     * @author 140024
     * @implNote 행번 (7140024)
     * @since 2024-09-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 조회 기준년도 (2024)
     * @since 2024-09-11
     */
    private String stdYear;

    /***
     * @author 140024
     * @implNote 회사코드
     * @since 2024-09-11
     */
    private String companyCd = "01";

        /***
     * @author 140024
     * @implNote 국가/지역코드
     * @since 2024-09-11
     */
    private String localeCd = "KO";

}
