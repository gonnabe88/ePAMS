package epams.domain.com.workTime;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * @author 210058
 * @implNote 근무시간 DTO
 * @since 2024-10-13
 */
@Getter
@Slf4j
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkTimeDTO {

    private Long empId;//행번

    private String empNm;//이름

    private String ymd;//날짜

    private String week;//요일

    private String workType;//근무유형

    private String staTime;//출근시간

    private String endTime;//퇴근시간

}
