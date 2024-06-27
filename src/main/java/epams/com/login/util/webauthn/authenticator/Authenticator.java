package epams.com.login.util.webauthn.authenticator;

import java.util.Optional;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AttestedCredentialData;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;

import epams.com.login.util.webauthn.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "THURXE_CWCRDM")
@Comment("인사_외부근태 Webauthn자격증명기본")
public class Authenticator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "WEBAUTHN_SNO", columnDefinition = "NUMBER(10)")
    @Comment("Webauthn 일련번호")
    private Long id;

    @Lob
    @Column(name = "WEBAUTHN_CRDT_ID", nullable = false)
    @Comment("Webauthn 자격증명 ID")
    private ByteArray credentialId;

    @Lob
    @Column(name = "WEBAUTHN_PBLK_KEY", nullable = false)
    @Comment("Webauthn 공개키")
    private ByteArray publicKey;

	@Column(name = "WEBAUTHN_CNT", nullable = false, columnDefinition = "NUMBER(10)")
    @Comment("Webauthn 자격증명수")
	private Long count;
	
	@Lob
	@Column(name = "WEBAUTHN_AAGUID", nullable = true)
    @Comment("Webauthn Authenticator Attestation GUID")
	private ByteArray aaguid;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name="ENO", nullable = false)
    @Comment("직원번호")
    private AppUser user;
	

public Authenticator(RegistrationResult result, AuthenticatorAttestationResponse response, AppUser user) {
    Optional<AttestedCredentialData> attestationData = response.getAttestation().getAuthenticatorData().getAttestedCredentialData();
    this.credentialId = result.getKeyId().getId();
    this.publicKey = result.getPublicKeyCose();
    this.aaguid = attestationData.get().getAaguid();
    this.count = result.getSignatureCount();
    this.user = user;
}
}
