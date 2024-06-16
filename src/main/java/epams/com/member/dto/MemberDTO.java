package epams.com.member.dto;

import epams.com.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String username; //행번
    private String password; //패스워드 
    private String dept;
    private String team;
    private MemberRole role = MemberRole.ROLE_KDB; //권한
    private String OTP;
    private String UUID;
    private String MFA;
    private String responsibility;
    
    public static MemberDTO toSearchDTO(MemberEntity memberEntity) {
    	MemberDTO memberDTO = new MemberDTO();
    	memberDTO.setUsername(memberEntity.getUsername());
    	memberDTO.setDept(memberEntity.getDept());
    	memberDTO.setTeam(memberEntity.getTeam());
    	memberDTO.setResponsibility(memberEntity.getResponsibility());
    	
    	return memberDTO;
    }
    
}