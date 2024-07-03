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

import epams.com.login.util.webauthn.authenticator.AuthenticatorRepository;
import epams.com.login.util.webauthn.authenticator.Authenticator;
import epams.com.login.util.webauthn.user.UserRepository;
import epams.com.login.util.webauthn.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author K140024
 * @implNote 간편인증 등록을 위한 서비스
 * @since 2024-06-11
 */
@Slf4j
@Getter
@NoArgsConstructor
@Repository
public class RegistrationService implements CredentialRepository {

    /**
     * @author K140024
     * @implNote WebauthUserRepository 주입
     * @since 2024-06-11
     */
    @Autowired
    private UserRepository userRepo;

    /**
     * @author K140024
     * @implNote WebauthDetailRepository 주입
     * @since 2024-06-11
     */
    @Autowired
    private AuthenticatorRepository authRepository;

    /**
     * @author K140024
     * @implNote 사용자 이름으로 자격 증명 ID를 가져오는 메서드
     * @since 2024-06-11
     */
    @Override
    public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(final String username) {
        log.warn("getCredentialIdsForUsername");
        final AppUser user = userRepo.findByUsername(username);
        final List<Authenticator> auth = authRepository.findAllByUser(user);
        return auth.stream()
            .map(
                credential -> PublicKeyCredentialDescriptor.builder()
                    .id(credential.getCredentialId())
                    .build())
            .collect(Collectors.toSet());
    }

    /**
     * @author K140024
     * @implNote 사용자 이름으로 사용자 핸들을 가져오는 메서드
     * @since 2024-06-11
     */
    @Override
    public Optional<ByteArray> getUserHandleForUsername(final String username) {
        log.warn("getUserHandleForUsername");
        final AppUser user = userRepo.findByUsername(username);
        return Optional.of(user.getHandle());
    }

    /**
     * @author K140024
     * @implNote 사용자 핸들로 사용자 이름을 가져오는 메서드
     * @since 2024-06-11
     */
    @Override
    public Optional<String> getUsernameForUserHandle(final ByteArray userHandle) {
        log.warn("getUsernameForUserHandle");
        final AppUser user = userRepo.findByHandle(userHandle);
        return Optional.of(user.getUsername());
    }

    /**
     * @author K140024
     * @implNote 자격 증명 ID와 사용자 핸들로 자격 증명을 조회하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Optional<RegisteredCredential> lookup(final ByteArray credentialId, final ByteArray userHandle) {
        final Optional<Authenticator> auth = authRepository.findByCredentialId(credentialId);

        return auth.map(
            credential -> RegisteredCredential.builder()
                .credentialId(credential.getCredentialId())
                .userHandle(credential.getUser().getHandle())
                .publicKeyCose(credential.getPublicKey())
                .signatureCount(credential.getCount())
                .build()
        );
    }

    /**
     * @author K140024
     * @implNote 자격 증명 ID로 모든 자격 증명을 조회하는 메서드
     * @since 2024-06-11
     */
    @Override
    public Set<RegisteredCredential> lookupAll(final ByteArray credentialId) {
        log.warn("lookupAll");
        final List<Authenticator> auth = authRepository.findAllByCredentialId(credentialId);
        return auth.stream()
            .map(
                credential -> RegisteredCredential.builder()
                    .credentialId(credential.getCredentialId())
                    .userHandle(credential.getUser().getHandle())
                    .publicKeyCose(credential.getPublicKey())
                    .signatureCount(credential.getCount())
                    .build())
            .collect(Collectors.toSet());
    }
}
