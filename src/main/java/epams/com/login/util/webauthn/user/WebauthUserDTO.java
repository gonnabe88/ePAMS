package epams.com.login.util.webauthn.user;

import java.util.List;
import java.util.stream.Collectors;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
public class WebauthUserDTO {
    
    private String username;

    private String displayName;

    private ByteArray handle;

    public WebauthUserDTO(UserIdentity user) {
        this.handle = user.getId();
        this.username = user.getName();
        this.displayName = user.getDisplayName();
    }

    public UserIdentity toUserIdentity() {
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }
    
    public static WebauthUserDTO toDTO(WebauthUserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new WebauthUserDTO(
            entity.getEMP_NO(),
            entity.getDISP_NM(),
            entity.getHANDLE() // handle 값 설정
        );
    }

    public WebauthUserEntity toEntity() {
        WebauthUserEntity entity = new WebauthUserEntity();
        entity.setEMP_NO(this.getUsername());
        entity.setDISP_NM(this.getDisplayName());
        entity.setHANDLE(this.getHandle()); // handle 값 설정
        return entity;
    }

    public static List<WebauthUserDTO> toDTOList(List<WebauthUserEntity> entityList) {
        return entityList.stream()
            .map(WebauthUserDTO::toDTO)
            .collect(Collectors.toList());
    }

    public static List<WebauthUserEntity> toEntityList(List<WebauthUserDTO> dtoList) {
        return dtoList.stream()
            .map(WebauthUserDTO::toEntity)
            .collect(Collectors.toList());
    }

    public WebauthUserDTO(String username, String displayName, ByteArray handle) {
        this.username = username;
        this.displayName = displayName;
        this.handle = handle;
    }
    

}