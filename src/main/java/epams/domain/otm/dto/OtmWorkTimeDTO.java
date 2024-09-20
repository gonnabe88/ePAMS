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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtmWorkTimeDTO extends OtmSqlParamDTO {

    /***
     * @author 140024
     * @implNote 근무시간
     * @since 2024-09-11
     */
    private String workTime;

}
