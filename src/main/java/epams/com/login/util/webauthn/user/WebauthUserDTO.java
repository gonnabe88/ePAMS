package epams.com.login.util.webauthn.user;

import java.util.List;
import java.util.stream.Collectors;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author K140024
 * @implNote 간편인증 사용자 정보 DTO
 * @since 2024-06-11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class WebauthUserDTO {

    /**
     * @author K140024
     * @implNote 사용자 이름
     * @since 2024-06-11
     */
    private String username;

    /**
     * @author K140024
     * @implNote 표시 이름
     * @since 2024-06-11
     */
    private String displayName;

    /**
     * @author K140024
     * @implNote 사용자 핸들
     * @since 2024-06-11
     */
    private ByteArray handle;

    /**
     * @author K140024
     * @implNote UserIdentity를 사용하여 WebauthUserDTO 객체를 생성하는 생성자
     * @since 2024-06-11
     */
    public WebauthUserDTO(final UserIdentity user) {
        this.handle = user.getId();
        this.username = user.getName();
        this.displayName = user.getDisplayName();
    }

    /**
     * @author K140024
     * @implNote 사용자 이름, 표시 이름 및 핸들을 사용하여 WebauthUserDTO 객체를 생성하는 생성자
     * @since 2024-06-11
     */
    public WebauthUserDTO(final String username, final String displayName, final ByteArray handle) {
        this.username = username;
        this.displayName = displayName;
        this.handle = handle;
    }

    /**
     * @author K140024
     * @implNote WebauthUserDTO 객체를 UserIdentity 객체로 변환하는 메서드
     * @since 2024-06-11
     */
    public UserIdentity toUserIdentity() {
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }

    /**
     * @author K140024
     * @implNote WebauthUserEntity 객체를 WebauthUserDTO 객체로 변환하는 메서드
     * @since 2024-06-11
     */
    public static WebauthUserDTO toDTO(final WebauthUserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new WebauthUserDTO(
            entity.getEMP_NO(),
            entity.getDISP_NM(),
            entity.getHANDLE() // handle 값 설정
        );
    }

    /**
     * @author K140024
     * @implNote WebauthUserDTO 객체를 WebauthUserEntity 객체로 변환하는 메서드
     * @since 2024-06-11
     */
    public WebauthUserEntity toEntity() {
        final WebauthUserEntity entity = new WebauthUserEntity();
        entity.setEMP_NO(this.getUsername());
        entity.setDISP_NM(this.getDisplayName());
        entity.setHANDLE(this.getHandle()); // handle 값 설정
        return entity;
    }

    /**
     * @author K140024
     * @implNote WebauthUserEntity 리스트를 WebauthUserDTO 리스트로 변환하는 메서드
     * @since 2024-06-11
     */
    public static List<WebauthUserDTO> toDTOList(final List<WebauthUserEntity> entityList) {
        return entityList.stream()
            .map(WebauthUserDTO::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * @author K140024
     * @implNote WebauthUserDTO 리스트를 WebauthUserEntity 리스트로 변환하는 메서드
     * @since 2024-06-11
     */
    public static List<WebauthUserEntity> toEntityList(final List<WebauthUserDTO> dtoList) {
        return dtoList.stream()
            .map(WebauthUserDTO::toEntity)
            .collect(Collectors.toList());
    }
}
