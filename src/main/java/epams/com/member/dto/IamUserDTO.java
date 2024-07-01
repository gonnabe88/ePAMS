package epams.com.member.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import epams.com.member.entity.IamUserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***
 * @author 140024
 * @implNote 사용자
 * @since 2024-06-30
 */
@NoArgsConstructor
@Getter
@Setter
public class IamUserDTO {

    /***
     * @author 140024
     * @implNote 행번(EMP_NO)
     * @since 2024-06-30
     */
    private String username;

    /***
     * @author 140024
     * @implNote 사용자명(EMP_NM)
     * @since 2024-06-30
     */
    private String realname;

    /***
     * @author 140024
     * @implNote 패스워드(EMP_PWD)
     * @since 2024-06-30
     */
    private String password;

    /***
     * @author 140024
     * @implNote 생성일자(TZ_DATE)
     * @since 2024-06-30
     */
    private LocalDate createdDate;


    public static IamUserDTO toDTO(IamUserEntity entity) {
        if (entity == null) {
            return null;
        }
        IamUserDTO dto = new IamUserDTO();
        dto.setUsername(entity.getEMP_NO());
        dto.setRealname(entity.getEMP_NM());
        dto.setPassword(entity.getEMP_PWD());
        dto.setCreatedDate(entity.getTZ_DATE());
        return dto;
    }

    public static List<IamUserDTO> toDTOs(List<IamUserEntity> entities) {
        return entities.stream().map(IamUserDTO::toDTO).collect(Collectors.toList());
    }

    public static List<IamUserEntity> toEntities(List<IamUserDTO> dtos) {
        return dtos.stream().map(IamUserDTO::toEntity).collect(Collectors.toList());
    }

    public IamUserEntity toEntity() {
        IamUserEntity entity = new IamUserEntity();
        entity.setEMP_NO(this.getUsername());
        entity.setEMP_NM(this.getRealname());
        entity.setEMP_PWD(this.getPassword());
        entity.setTZ_DATE(this.getCreatedDate());
        return entity;
    }
}
