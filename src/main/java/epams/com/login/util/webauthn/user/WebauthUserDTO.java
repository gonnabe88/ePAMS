package epams.com.login.util.webauthn.user;

import java.util.List;
import java.util.stream.Collectors;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WebauthUserDTO {

    private String username;
    private String displayName;
    private ByteArray handle;

    public WebauthUserDTO(final UserIdentity user) {
        log.warn("[WebauthUserDTO 생성자 (UserIdentity)] : " + user.getId() + " / " + 
        user.getName() + " + " + user.getDisplayName());
        this.handle = user.getId();
        this.username = user.getName();
        this.displayName = user.getDisplayName();
    }

    public WebauthUserDTO(final String username, final String displayName, final ByteArray handle) {
        log.warn("[WebauthUserDTO 생성자 (3개 인자)] : " + username + " / " + 
        displayName + " + " + handle);
        this.username = username;
        this.displayName = displayName;
        this.handle = handle;
    }

    public UserIdentity toUserIdentity() {
        log.warn("[WebauthUserDTO UserIdentity toUserIdentity()] : " + getUsername() + " / " + 
        getDisplayName() + " + " + getHandle());
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }

    public static WebauthUserDTO toDTO(final WebauthUserEntity entity) {
        if (entity == null) {
            return null;
        }
        log.warn("[WebauthUserDTO toDTO] : " + entity.getEMP_NO() + " / " + 
            entity.getDISP_NM() + " + " + new ByteArray(entity.getHANDLE()));
        return new WebauthUserDTO(
            
            entity.getEMP_NO(),
            entity.getDISP_NM(),
            new ByteArray(entity.getHANDLE()) // handle 값을 ByteArray로 변환하여 설정
        );
    }

    public WebauthUserEntity toEntity() {
        final WebauthUserEntity entity = new WebauthUserEntity();
        entity.setEMP_NO(this.getUsername());
        entity.setDISP_NM(this.getDisplayName());
        entity.setHANDLE(this.getHandle().getBytes()); // ByteArray를 byte[]로 변환하여 설정
        log.warn("[WebauthUserDTO toEntity] : " + entity.getEMP_NO() + " / " + 
            entity.getDISP_NM() + " + " + new ByteArray(entity.getHANDLE()));
        return entity;
    }

    public static List<WebauthUserDTO> toDTOList(final List<WebauthUserEntity> entityList) {
        return entityList.stream()
            .map(WebauthUserDTO::toDTO)
            .collect(Collectors.toList());
    }

    public static List<WebauthUserEntity> toEntityList(final List<WebauthUserDTO> dtoList) {
        return dtoList.stream()
            .map(WebauthUserDTO::toEntity)
            .collect(Collectors.toList());
    }
}
