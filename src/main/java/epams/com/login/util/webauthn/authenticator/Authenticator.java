package epams.com.login.util.webauthn.authenticator;

import java.util.Optional;

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
@Table(name = "HURXE_WWCREM")
public class Authenticator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "SEQ_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @Lob
    @Column(name = "CRDT_ID", nullable = false)
    private ByteArray credentialId;

    @Lob
    @Column(name = "PBLK_KEY", nullable = false)
    private ByteArray publicKey;

	@Column(name = "CNT", nullable = false)
	private Long count;
	
	@Lob
	@Column(name = "AAGUID", nullable = true)
	private ByteArray aaguid;

    @ManyToOne(targetEntity = AppUser.class)
    @JoinColumn(name="EMP_NO", nullable = false)
    private AppUser user;
	

public Authenticator(RegistrationResult result, AuthenticatorAttestationResponse response, AppUser user, String name) {
    Optional<AttestedCredentialData> attestationData = response.getAttestation().getAuthenticatorData().getAttestedCredentialData();
    this.credentialId = result.getKeyId().getId();
    this.publicKey = result.getPublicKeyCose();
    this.aaguid = attestationData.get().getAaguid();
    this.count = result.getSignatureCount();
    this.name = name;
    this.user = user;
}
}
