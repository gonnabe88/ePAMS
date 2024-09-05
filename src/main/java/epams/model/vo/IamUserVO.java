package epams.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/***
 * 사용자 정보를 담는 DTO 클래스입니다.
 * 
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class IamUserVO {

    /***
     * 사용자 행번(EMP_NO)
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String username;

    /***
     * 사용자명(EMP_NM)
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String realname;

    /***
     * 사용자 패스워드(EMP_PWD)
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String password;

    /***
     * 생성일자(TZ_DATE)
     * 
     * @author 140024
     * @since 2024-06-30
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
}
