package epams.com.login.util.webauthn.authenticator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AttestedCredentialData;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;

import epams.com.login.util.webauthn.user.WebauthUserDTO;
import epams.com.login.util.webauthn.user.WebauthUserEntity;
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
public class WebauthDetailDTO {

    private Long seqId;

    private ByteArray credentialId;

    private ByteArray publicKey;

    private Long count;

    private ByteArray aaguid;

    private WebauthUserDTO user; // WebauthUserDTO를 사용하여 user 정보를 포함

    public WebauthDetailDTO(RegistrationResult result, AuthenticatorAttestationResponse response, WebauthUserDTO user, String name) {
        Optional<AttestedCredentialData> attestationData = response.getAttestation().getAuthenticatorData().getAttestedCredentialData();
        this.credentialId = result.getKeyId().getId();
        this.publicKey = result.getPublicKeyCose();
        this.aaguid = attestationData.get().getAaguid();
        this.count = result.getSignatureCount();
        this.user = user; // WebauthUserDTO로 변환
    }

    public static WebauthDetailDTO toDTO(WebauthDetailEntity entity) {
        if (entity == null) {
            return null;
        }
        WebauthDetailDTO dto = new WebauthDetailDTO();
        dto.setSeqId(entity.getSEQ_ID());
        dto.setCredentialId(entity.getCRDT_ID());
        dto.setPublicKey(entity.getPBLK_KEY());
        dto.setCount(entity.getCNT());
        dto.setAaguid(entity.getAAGUID());
        dto.setUser(WebauthUserDTO.toDTO(entityget()));
        if (entity.getUser() != null) {
            WebauthUserDTO userDTO = new WebauthUserDTO();
            userDTO.setUsername(entity.getEMP_NO());
            userDTO.setDisplayName(entity.getUser().getDISP_NM());
            userDTO.setHandle(entity.getUser().getHANDLE());
            dto.setUser(null);
        }
        return dto;
    }

    public WebauthDetailEntity toEntity() {
        WebauthDetailEntity entity = new WebauthDetailEntity();
        entity.setSEQ_ID(this.getSeqId());
        entity.setCRDT_ID(this.getCredentialId());
        entity.setPBLK_KEY(this.getPublicKey());
        entity.setCNT(this.getCount());
        entity.setAAGUID(this.getAaguid());
        entity.setUser(this.getUser().toEntity());
        return entity;
    }
    public static List<WebauthDetailDTO> toDTOList(List<WebauthDetailEntity> entityList) {
        return entityList.stream()
            .map(WebauthDetailDTO::toDTO)
            .collect(Collectors.toList());
    }

    public static List<WebauthDetailEntity> toEntityList(List<WebauthDetailDTO> dtoList) {
        return dtoList.stream()
            .map(WebauthDetailDTO::toEntity)
            .collect(Collectors.toList());
    }
}