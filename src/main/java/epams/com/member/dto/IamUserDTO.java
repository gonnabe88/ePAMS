package epams.com.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.member.entity.IamUserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 사용자 정보를 담는 DTO 클래스입니다.
 * 
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class IamUserDTO {

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

    /***
     * 로그인 유형
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String MFA;
}
