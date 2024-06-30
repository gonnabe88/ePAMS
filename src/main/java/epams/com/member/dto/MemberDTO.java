package epams.com.member.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/***
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class MemberDTO {

    /***
     * @author 140024
     * @implNote 사원번호(ENO)
     * @since 2024-06-30
     */
    private String username;

    /***
     * @author 140024
     * @implNote 관리자여부(ADMIN_YN)
     * @implSpec 외래키
     * @since 2024-06-30
     */
    private String adminYn = "0";

    /***
     * @author 140024
     * @implNote 마지막로그인일시(LAST_LOGIN_DATE)
     * @implSpec 외래키
     * @since 2024-06-30
     */
    private LocalDate lastLoginTime;

    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static MemberDTO toDTO(final MemberEntity memberEntity) {
        final MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUsername(memberEntity.getENO());
        memberDTO.setAdminYn(memberEntity.getADMIN_YN());
        memberDTO.setLastLoginTime(memberEntity.getLAST_LOGIN_DATE());
        return memberDTO;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<MemberDTO> toDTOs(List<MemberEntity> memberEntities) {
        return memberEntities.stream()
                .map(MemberDTO::toDTO)
                .collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public MemberEntity toEntity() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setENO(this.getUsername());
        memberEntity.setADMIN_YN(this.getAdminYn());
        memberEntity.setLAST_LOGIN_DATE(this.getLastLoginTime());
        return memberEntity;
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<MemberEntity> toEntities(List<MemberDTO> memberDTOs) {
        return memberDTOs.stream()
                .map(MemberDTO::toEntity)
                .collect(Collectors.toList());
    }

}
