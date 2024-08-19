package epams.domain.com.admin.dto;

import epams.model.vo.LogLoginVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/***
 * @author 140024
 * @implNote Login Log 데이터를 객체로 관리하기 위한 DTO
 * @since 2024-06-09
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class LogLoginDTO extends LogLoginVO {

    /***
     * @author 140024
     * @implNote DTO 생성 메소드
     * @since 2024-06-09
     */
    public static LogLoginDTO getDTO(final String empNo, final String loginType, final String loginResult) {
    	final LogLoginDTO logLoginDTO = new LogLoginDTO();
    	logLoginDTO.setEmpNo(empNo);
    	logLoginDTO.setLoginType(loginType);
    	logLoginDTO.setLoginResult(loginResult);
        return logLoginDTO;
    }
}