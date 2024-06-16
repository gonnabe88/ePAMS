package epams.com.login.util.webauthn.authenticator;

import java.util.List;
import java.util.Optional;

import com.yubico.webauthn.data.ByteArray;

import epams.com.login.util.webauthn.user.AppUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatorRepository extends CrudRepository<Authenticator, Long> {
    Optional<Authenticator> findByCredentialId(ByteArray credentialId);
    List<Authenticator> findAllByUser (AppUser user);
    Authenticator findByUser (AppUser user);
    List<Authenticator> findAllByCredentialId(ByteArray credentialId);
}
