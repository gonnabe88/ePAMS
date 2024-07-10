package epams.com.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.admin.entity.LogLoginEntity;
import epams.com.board.dto.BaseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class LogLoginDTO extends BaseDTO {

    /***
     * @author 140024
     * @implNote 로그인로그일련번호(LGN_LOG_SNO)
     * @since 2024-06-09
     */
    private Long seqId;

    /***
     * @author 140024
     * @implNote 사원번호 (ENO)
     * @since 2024-06-09
     */
    private String empNo;

    /***
     * @author 140024
     * @implNote 인증방식 (CER_KD_NM)
     * @since 2024-06-09
     */
    private String loginType;

    /***
     * @author 140024
     * @implNote 로그인성공여부 (LGN_SCS_YN)
     * @since 2024-06-09
     */
    private char loginResult;

    /***
     * @author 140024
     * @implNote DTO 생성 메소드
     * @since 2024-06-09
     */
    public static LogLoginDTO getDTO(final String empNo, final String loginType, final char loginResult) {
    	final LogLoginDTO logLoginDTO = new LogLoginDTO();
    	logLoginDTO.setEmpNo(empNo);
    	logLoginDTO.setLoginType(loginType);
    	logLoginDTO.setLoginResult(loginResult);
        return logLoginDTO;
    }
    
}