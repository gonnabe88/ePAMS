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

    /***
     * 엔티티 객체를 DTO로 변환하는 메서드입니다.
     * 
     * @author 140024
     * @since 2024-06-30
     */
    public static IamUserDTO toDTO(final IamUserEntity entity) {
        final IamUserDTO dto = new IamUserDTO();
        if (entity != null) {
            dto.setUsername(entity.getEMP_NO());
            dto.setRealname(entity.getEMP_NM());
            dto.setPassword(entity.getEMP_PWD());
            dto.setCreatedDate(entity.getTZ_DATE());
        }
        return dto;
    }

    /***
     * 엔티티 객체 리스트를 DTO 리스트로 변환하는 메서드입니다.
     * 
     * @author 140024
     * @since 2024-06-30
     */
    public static List<IamUserDTO> toDTOs(final List<IamUserEntity> entities) {
        return entities.stream().map(IamUserDTO::toDTO).collect(Collectors.toList());
    }

    /***
     * DTO 객체 리스트를 엔티티 리스트로 변환하는 메서드입니다.
     * 
     * @author 140024
     * @since 2024-06-30
     */
    public static List<IamUserEntity> toEntities(final List<IamUserDTO> dtos) {
        return dtos.stream().map(IamUserDTO::toEntity).collect(Collectors.toList());
    }

    /***
     * DTO 객체를 엔티티 객체로 변환하는 메서드입니다.
     * 
     * @author 140024
     * @since 2024-06-30
     */
    public IamUserEntity toEntity() {
        final IamUserEntity entity = new IamUserEntity();
        entity.setEMP_NO(this.getUsername());
        entity.setEMP_NM(this.getRealname());
        entity.setEMP_PWD(this.getPassword());
        entity.setTZ_DATE(this.getCreatedDate());
        return entity;
    }

    /***
     * IamUserDTO 생성자입니다.
     * 
     * @author 140024
     * @since 2024-06-30
     */
    @Builder
    public IamUserDTO(final String username, final String realname, final String password, final LocalDateTime createdDate) {
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.createdDate = createdDate;
    }
}
