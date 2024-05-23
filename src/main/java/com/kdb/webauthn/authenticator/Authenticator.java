package com.kdb.webauthn.authenticator;

import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import com.kdb.webauthn.user.AppUser;
import com.yubico.webauthn.RegistrationResult;
import com.yubico.webauthn.data.AttestedCredentialData;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ByteArray;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Authenticator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String name;

    @Lob
    @Column(nullable = false)
    private ByteArray credentialId;

    @Lob
    @Column(nullable = false)
    private ByteArray publicKey;

@Column(nullable = false)
private Long count;

@Lob
@Column(nullable = true)
private ByteArray aaguid;

    @ManyToOne
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
