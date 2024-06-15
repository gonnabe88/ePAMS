package com.kdb.com.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.kdb.com.entity.HtmlEntity;
import com.kdb.com.entity.LogLoginEntity;

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
public class LogLoginDTO {
	
    /***
     * @author 140024
     * @implNote 행번 (EMP_NO)
     * @since 2024-06-09
     */
    private String empNo;
    /***
     * @author 140024
     * @implNote 인증방식 (TYPE)
     * @since 2024-06-09
     */
    private String loginType; 
    /***
     * @author 140024
     * @implNote 성공여부 (RESULT)
     * @since 2024-06-09
     */
    private boolean loginResult;
    /***
     * @author 140024
     * @implNote 생성시간 (CREATED_TIME)
     * @since 2024-06-09
     */
    private LocalDateTime createdTime;
    
    /***
     * @author 140024
     * @implNote DTO 생성 메소드
     * @since 2024-06-09
     */
    public static LogLoginDTO getDTO(String empNo, String loginType, Boolean loginResult) {
    	LogLoginDTO logLoginDTO = new LogLoginDTO();
    	logLoginDTO.setEmpNo(empNo);
    	logLoginDTO.setLoginType(loginType);
    	logLoginDTO.setLoginResult(loginResult);
        return logLoginDTO;
    }
    
    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static LogLoginDTO toDTO(LogLoginEntity logLoginEntity) {
    	LogLoginDTO logLoginDTO = new LogLoginDTO();
    	logLoginDTO.setEmpNo(logLoginEntity.getEMP_NO());
    	logLoginDTO.setLoginType(logLoginEntity.getLOGIN_TYPE());
    	logLoginDTO.setLoginResult(logLoginEntity.isLOGIN_RESULT());
    	logLoginDTO.setCreatedTime(logLoginEntity.getCREATED_TIME());
        return logLoginDTO;
    }
    
    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public LogLoginEntity toEntity() {
    	LogLoginEntity logLoginEntity = new LogLoginEntity();
    	logLoginEntity.setEMP_NO(this.empNo);
    	logLoginEntity.setLOGIN_TYPE(this.loginType);
    	logLoginEntity.setLOGIN_RESULT(this.loginResult);
    	logLoginEntity.setCREATED_TIME(this.createdTime);
        return logLoginEntity;
    }
    
    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LogLoginDTO> toDTOList(List<LogLoginEntity> logLoginEntity) {
        return logLoginEntity.stream()
                .map(LogLoginDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<DTO> > List<Entity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<LogLoginEntity> toEntityList(List<LogLoginDTO> LogLoginDTOs) {
        return LogLoginDTOs.stream()
                .map(LogLoginDTO::toEntity)
                .collect(Collectors.toList());
    }
    
}