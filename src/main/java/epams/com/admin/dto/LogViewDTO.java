package epams.com.admin.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.admin.entity.LogViewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class LogViewDTO {
	
    /***
     * @author 140024
     * @implNote 행번 (EMP_NO)
     * @since 2024-06-09
     */
    private Long seqId;
    
    /***
     * @author 140024
     * @implNote 인증방식 (TYPE)
     * @since 2024-06-09
     */
    private String controllerName; 

    /***
     * @author 140024
     * @implNote 호출 메소드명
     * @since 2024-06-09
     */
    private String methodName;
    
    /***
     * @author 140024
     * @implNote 접속 IP
     * @since 2024-06-09
     */
    private String clientIp;
    
    /***
     * @author 140024
     * @implNote 단말정보
     * @since 2024-06-09
     */
    private String userAgent;
    
    /***
     * @author 140024
     * @implNote 요청 URL
     * @since 2024-06-09
     */
    private String requestUrl;
    
    /***
     * @author 140024
     * @implNote 호출시간
     * @since 2024-06-09
     */
    private LocalDateTime callTime;
    
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static LogViewDTO toDTO(LogViewEntity entity) {
        return new LogViewDTO(
            entity.getSEQ_ID(),
            entity.getCTRL_NM(),
            entity.getMTHD_NM(),
            entity.getCLIENT_IP(),
            entity.getUSER_AGENT(),
            entity.getRQST_URL(),
            entity.getCALL_TIME()
        );
    }
    
    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public static LogViewEntity toEntity(LogViewDTO dto) {
        return LogViewEntity.builder()
            .SEQ_ID(dto.getSeqId())
            .CTRL_NM(dto.getControllerName())
            .MTHD_NM(dto.getMethodName())
            .CLIENT_IP(dto.getClientIp())
            .USER_AGENT(dto.getUserAgent())
            .RQST_URL(dto.getRequestUrl())
            .CALL_TIME(dto.getCallTime())
            .build();
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LogViewDTO> toDTOList(List<LogViewEntity> entityList) {
        return entityList.stream()
                         .map(LogViewDTO::toDTO)
                         .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LogViewEntity> toEntityList(List<LogViewDTO> dtoList) {
        return dtoList.stream()
                      .map(LogViewDTO::toEntity)
                      .collect(Collectors.toList());
    }
    
}