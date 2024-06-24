package epams.com.login.util.webauthn.authenticator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AttestedCredentialData;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;

import epams.com.login.util.webauthn.user.WebauthUserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author K140024
 * @implNote WebauthDetailDTO 클래스
 * @since 2024-04-26
 */
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
public class WebauthDetailDTO {

    /**
     * 시퀀스 ID
     */
    private Long seqId;

    /**
     * 자격 증명 ID
     */
    private ByteArray credentialId;

    /**
     * 공개 키
     */
    private ByteArray publicKey;

    /**
     * 서명 횟수
     */
    private Long count;

    /**
     * AAGUID
     */
    private ByteArray aaguid;

    /**
     * 사용자 정보
     */
    private WebauthUserDTO user;

    /**
     * 생성자
     * 
     * @param result   RegistrationResult 객체
     * @param response AuthenticatorAttestationResponse 객체
     * @param user     WebauthUserDTO 객체
     * @param name     사용자 이름
     */
    public WebauthDetailDTO(final RegistrationResult result, final AuthenticatorAttestationResponse response, final WebauthUserDTO user) {
        final Optional<AttestedCredentialData> attestationData = response.getAttestation().getAuthenticatorData().getAttestedCredentialData();
        this.credentialId = result.getKeyId().getId();
        this.publicKey = result.getPublicKeyCose();
        this.aaguid = attestationData.get().getAaguid();
        this.count = result.getSignatureCount();
        this.user = user;
    }

    /**
     * 생성자
     * 
     * @param result   RegistrationResult 객체
     * @param response AuthenticatorAttestationResponse 객체
     * @param user     WebauthUserDTO 객체
     * @param name     사용자 이름
     */
    public WebauthDetailDTO(ByteArray credentialId, ByteArray publicKey, Long count, ByteArray aaguid, WebauthUserDTO user) {
        this.credentialId = credentialId;
        this.publicKey = publicKey;
        this.count = count;
        this.aaguid = aaguid;
        this.user = user;
    }

    /**
     * WebauthDetailEntity 객체를 WebauthDetailDTO 객체로 변환
     * 
     * @param entity WebauthDetailEntity 객체
     * @return WebauthDetailDTO 객체
     */
    public static WebauthDetailDTO toDTO(final WebauthDetailEntity entity) {
        if (entity == null) {
            return null;
        }
        return new WebauthDetailDTO(
            new ByteArray(entity.getCRDT_ID()),
            new ByteArray(entity.getPBLK_KEY()),
            entity.getCNT(),
            new ByteArray(entity.getAAGUID()),
            WebauthUserDTO.toDTO(entity.getUser())
        );
    }

    /**
     * WebauthDetailDTO 객체를 WebauthDetailEntity 객체로 변환
     * 
     * @return WebauthDetailEntity 객체
     */
    public WebauthDetailEntity toEntity() {
        final WebauthDetailEntity entity = new WebauthDetailEntity();
        entity.setSEQ_ID(this.getSeqId());
        entity.setCRDT_ID(this.getCredentialId().getBytes());
        entity.setPBLK_KEY(this.getPublicKey().getBytes());
        entity.setCNT(this.getCount());
        entity.setAAGUID(this.getAaguid().getBytes());
        entity.setUser(this.getUser().toEntity());
        return entity;
    }

    /**
     * WebauthDetailEntity 목록을 WebauthDetailDTO 목록으로 변환
     * 
     * @param entityList WebauthDetailEntity 목록
     * @return WebauthDetailDTO 목록
     */
    public static List<WebauthDetailDTO> toDTOList(final List<WebauthDetailEntity> entityList) {
        return entityList.stream()
            .map(WebauthDetailDTO::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * WebauthDetailDTO 목록을 WebauthDetailEntity 목록으로 변환
     * 
     * @param dtoList WebauthDetailDTO 목록
     * @return WebauthDetailEntity 목록
     */
    public static List<WebauthDetailEntity> toEntityList(final List<WebauthDetailDTO> dtoList) {
        return dtoList.stream()
            .map(WebauthDetailDTO::toEntity)
            .collect(Collectors.toList());
    }
}