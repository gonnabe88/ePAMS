package epams.domain.com.index.dto;

import epams.model.vo.IamOrgVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote 빠른신청 데이터 정의 DTO
 * @since 2024-06-09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamSearchDTO {
    /**
     * @author 140024
     * @implNote 팀코드
     * @since 2024-06-09
     */
    private String teamCode;

    /**
     * @author 140024
     * @implNote 팀명
     * @since 2024-08-19
     */
    private String teamName;
}
