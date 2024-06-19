package epams.com.login.util.webauthn;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;

import epams.com.login.util.webauthn.authenticator.WebauthDetailDTO;
import epams.com.login.util.webauthn.authenticator.WebauthDetailRepository;
import epams.com.login.util.webauthn.user.WebauthUserDTO;
import epams.com.login.util.webauthn.user.WebauthUserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Getter
public class RegistrationService implements CredentialRepository  {
    
    @Autowired
    private WebauthUserRepository webauthUserRepository;
    @Autowired
    private WebauthDetailRepository webauthDetailRepository;

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        WebauthUserDTO user = webauthUserRepository.findByUsername(username);
        List<WebauthDetailDTO> auth = webauthDetailRepository.findAllByUser(user.getUsername());
        return auth.stream()
        .map(
            credential ->
                PublicKeyCredentialDescriptor.builder()
                    .id(credential.getCredentialId())
                    .build())
        .collect(Collectors.toSet());
    }

    @Override
    public Optional<ByteArray> getUserHandleForUsername(String username) {
        WebauthUserDTO user = webauthUserRepository.findByUsername(username);
        return Optional.of(user.getHandle());
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
    	WebauthUserDTO user = webauthUserRepository.findByHandle(userHandle);
        return Optional.of(user.getUsername());
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        Optional<WebauthDetailDTO> auth = webauthDetailRepository.findByCredentialId(credentialId);
        log.warn(auth.toString());
        return auth.map(
            credential ->
                RegisteredCredential.builder()
                    .credentialId(credential.getCredentialId())
                    .userHandle(credential.getUser().getHandle())
                    .publicKeyCose(credential.getPublicKey())
                    .signatureCount(credential.getCount())
                    .build()
        );
    }

    @Override
    public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
        List<WebauthDetailDTO> auth = webauthDetailRepository.findAllByCredentialId(credentialId);
        return auth.stream()
        .map(
            credential ->
                RegisteredCredential.builder()
                    .credentialId(credential.getCredentialId())
                    .userHandle(credential.getUser().getHandle())
                    .publicKeyCose(credential.getPublicKey())
                    .signatureCount(credential.getCount())
                    .build())
        .collect(Collectors.toSet());
    }
}