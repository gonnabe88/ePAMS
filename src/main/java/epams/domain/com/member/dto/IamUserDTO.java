package epams.domain.com.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pams.model.vo.IamUserVO;

import java.time.LocalDateTime;

/***
 * 사용자 정보를 담는 DTO 클래스입니다.
 * 
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IamUserDTO extends IamUserVO {

    /***
     * 로그인 유형
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String MFA;
}