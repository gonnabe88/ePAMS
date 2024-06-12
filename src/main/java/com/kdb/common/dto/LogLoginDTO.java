package com.kdb.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
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
    private String type; 
    /***
     * @author 140024
     * @implNote 성공여부 (RESULT)
     * @since 2024-06-09
     */
    private boolean result;
    /***
     * @author 140024
     * @implNote 생성시간 (CREATED_TIME)
     * @since 2024-06-09
     */
    private LocalDateTime createdTime;
    
    /***
     * @author 140024
     * @implNote 기본 생성자
     * @since 2024-06-09
     */
    public LogLoginDTO(){ 
    	//Default Constructor 
    }

    /***
     * @author 140024
     * @implNote 생성자 /w 파라미터
     * @since 2024-06-09 최초작성
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public LogLoginDTO(final String empNo, final String type, final boolean result) {
        this.empNo = empNo;
        this.type = type;
        this.result = result;
        //this.CREATED_TIME = LocalDateTime.now();
    }

    /***
     * @author 140024
     * @implNote Factory Method
     * @since 2024-06-09 최초작성
     * @since 2024-06-09 PMD MethodArgumentCouldBeFinal 취약점 조치 (140024) 
     */
    public static LogLoginDTO getLogLoginDTO(final String empNo, final String type, final boolean result) {
        return new LogLoginDTO(empNo, type, result);
    }
    
}