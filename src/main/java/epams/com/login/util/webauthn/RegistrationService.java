package epams.com.login.util.webauthn;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;

import epams.com.login.util.webauthn.authenticator.Authenticator;
import epams.com.login.util.webauthn.authenticator.AuthenticatorRepository;
import epams.com.login.util.webauthn.user.AppUser;
import epams.com.login.util.webauthn.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

@Repository
@Getter
public class RegistrationService implements CredentialRepository  {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuthenticatorRepository authRepository;

    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
        AppUser user = userRepo.findByUsername(username);
        List<Authenticator> auth = authRepository.findAllByUser(user);
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
        AppUser user = userRepo.findByUsername(username);
        return Optional.of(user.getHandle());
    }

    @Override
    public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
        AppUser user = userRepo.findByHandle(userHandle);
        return Optional.of(user.getUsername());
    }

    @Override
    public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
        Optional<Authenticator> auth = authRepository.findByCredentialId(credentialId);
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
        List<Authenticator> auth = authRepository.findAllByCredentialId(credentialId);
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