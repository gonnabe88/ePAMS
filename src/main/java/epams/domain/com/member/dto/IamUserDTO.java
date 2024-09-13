package epams.domain.com.member.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import epams.model.vo.IamUserVO;

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
@ToString(callSuper = true)
@Builder
@Data
public class IamUserDTO extends IamUserVO {

    /***
     * @author 140024
     * @implNote 휴대폰 번호
     * @since 2024-09-05
     */
    private String phoneNo = "";

    /***
     * 2차인증 유형
     * 
     * @author 140024
     * @since 2024-06-30
     */
    private String MFA;

    /***
     * 로그인 유형
     *
     * @author 140024
     * @since 2024-08-11
     */
    private boolean isAdmin = false;

}
