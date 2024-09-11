package epams.domain.dtm.dto;

import lombok.*;
import pams.model.vo.DtmHisVO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
public class DtmAnnualStatusParamDTO {

    /***
     * @author 140024
     * @implNote 행번
     * @since 2024-09-11
     */
    private Long empId;

    /***
     * @author 140024
     * @implNote 조회 기준년도
     * @since 2024-09-11
     */
    private String stdYear;

}
