package epams.com.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import epams.com.member.entity.RoleEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class RoleDTO {

    /***
     * @author 140024
     * @implNote [Column] 사원번호(ENO)
     * @since 2024-06-30
     */
    private String username;

    /***
     * @author 140024
     * @implNote [Column] 권한ID(ROLE_ID)
     * @implSpec 외래키
     * @since 2024-06-30
     */
    private String roleId = "ROLE_ADMIN";

    /***
     * @author 140024
     * @implNote [Column] 수정일시(AMN_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    /***
     * @author 140024
     * @implNote [Column] 생성일시(GNT_DTM)
     * @since 2024-06-09
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /***
     * @author 140024
     * @implNote Entity > DTO 변경 메소드
     * @since 2024-06-09
     */
    public static RoleDTO toDTO(final RoleEntity roleEntity) {
        final RoleDTO roleDTO = new RoleDTO();
        roleDTO.setUsername(roleEntity.getENO());
        roleDTO.setRoleId(roleEntity.getATH_ID());
        roleDTO.setUpdatedTime(roleEntity.getAMN_DTM());
        roleDTO.setCreatedTime(roleEntity.getGNT_DTM());
        return roleDTO;
    }

    /***
     * @author 140024
     * @implNote List<Entity> > List<DTO> 변경 메소드
     * @since 2024-06-09
     */
    public static List<RoleDTO> toDTOs(final List<RoleEntity> memberEntities) {
        return memberEntities.stream().map(RoleDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote List<HtmlDTO> > List<CodeEntity> 변경 메소드
     * @since 2024-06-09
     */
    public static List<RoleEntity> toEntities(final List<RoleDTO> roleDTOS) {
        return roleDTOS.stream().map(RoleDTO::toEntity).collect(Collectors.toList());
    }

    /***
     * @author 140024
     * @implNote DTO > Entity 변경 메소드
     * @since 2024-06-09
     */
    public RoleEntity toEntity() {
        final RoleEntity roleEntity = new RoleEntity();
        roleEntity.setENO(this.getUsername());
        roleEntity.setATH_ID(this.getRoleId());
        roleEntity.setAMN_DTM(this.getUpdatedTime());
        roleEntity.setGNT_DTM(this.getCreatedTime());
        return roleEntity;
    }

}