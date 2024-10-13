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

    /**
     * @author 140024
     * @implNote 인사조직코드내용
     * @since 2024-08-19
     */
    private String deptCode;

    /**
     * @author 140024
     * @implNote 부점명
     * @since 2024-08-19
     */
    private String deptName;

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

    /**
     * @author 140024
     * @implNote 직위코드명
     * @since 2024-08-19
     */
    private String positionName;

    /**
     * @author 140024
     * @implNote 직무상세내용
     * @since 2024-08-19
     */
    private String jobDetail;

    /**
     * @author 140024
     * @implNote 내선번호
     * @since 2024-08-19
     */
    private String inlineNo;

    /***
     * @author 140024
     * @implNote 휴대폰 번호
     * @since 2024-09-05
     */
    private String phoneNo = "";

    /**
     * @author 140024
     * @implNote 내선번호(4자리)
     * @since 2024-08-19
     */
    private String simpleInlineNo;
}
