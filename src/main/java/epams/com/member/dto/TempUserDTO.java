package epams.com.member.dto;

import epams.com.member.entity.TempUserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author K140024
 * @implNote 회원 정보를 담는 DTO 클래스
 * @since 2024-06-11
 */
@Getter
@Setter
@ToString
public class TempUserDTO {
    
    /**
     * @author K140024
     * @implNote 사용자 이름(행번)
     * @since 2024-06-11
     */
    private String username;
    
    /**
     * @author K140024
     * @implNote 사용자 비밀번호
     * @since 2024-06-11
     */
    private String password;
    
    /**
     * @author K140024
     * @implNote 부서명
     * @since 2024-06-11
     */
    private String dept;
    
    /**
     * @author K140024
     * @implNote 팀명
     * @since 2024-06-11
     */
    private String team;
    
    /**
     * @author K140024
     * @implNote 사용자 역할(권한)
     * @since 2024-06-11
     */
    private TempRoleDTO role = TempRoleDTO.ROLE_NORMAL;
    
    /**
     * @author K140024
     * @implNote OTP 정보
     * @since 2024-06-11
     */
    private String OTP;
    
    /**
     * @author K140024
     * @implNote 사용자 UUID
     * @since 2024-06-11
     */
    private String UUID;
    
    /**
     * @author K140024
     * @implNote 멀티 팩터 인증(MFA) 정보
     * @since 2024-06-11
     */
    private String MFA;
    
    /**
     * @author K140024
     * @implNote 사용자 책임
     * @since 2024-06-11
     */
    private String responsibility;

    /**
     * @author K140024
     * @implNote MemberEntity 객체를 MemberDTO 객체로 변환하는 메서드
     * @since 2024-06-11
     */
    public static TempUserDTO toSearchDTO(TempUserEntity memberEntity) {
        final TempUserDTO memberDTO = new TempUserDTO();
        memberDTO.setUsername(memberEntity.getUsername());
        memberDTO.setDept(memberEntity.getDept());
        memberDTO.setTeam(memberEntity.getTeam());
        memberDTO.setResponsibility(memberEntity.getResponsibility());
        
        return memberDTO;
    }

    
}
