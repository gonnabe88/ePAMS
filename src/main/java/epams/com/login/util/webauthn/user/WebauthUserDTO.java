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
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
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
        WebauthUserDTO dto = new WebauthUserDTO();
        dto.username = entity.getEMP_NO();
        dto.displayName = entity.getDISP_NM();
        dto.handle = entity.getHANDLE();
        return dto;
    }

    public WebauthUserEntity toEntity() {
        WebauthUserEntity entity = new WebauthUserEntity();
        entity.setEMP_NO(this.username);
        entity.setDISP_NM(this.displayName);
        entity.setHANDLE(this.handle);
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
    

}