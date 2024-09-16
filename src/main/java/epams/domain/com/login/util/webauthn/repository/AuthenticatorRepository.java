package epams.domain.com.login.util.webauthn.repository;

import java.util.List;
import java.util.Optional;

import epams.domain.com.login.util.webauthn.authenticator.Authenticator;
import epams.domain.com.login.util.webauthn.user.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yubico.webauthn.data.ByteArray;

/**
 * @author K140024
 * @implNote Authenticator 엔티티에 대한 CRUD 리포지토리 인터페이스
 * @since 2024-06-11
 */
@Repository
public interface AuthenticatorRepository extends CrudRepository<Authenticator, Long> {

    /**
     * 주어진 자격증명 ID로 Authenticator를 조회합니다.
     *
     * @param credentialId 자격증명 ID
     * @return Optional<Authenticator> 자격증명 ID로 조회된 Authenticator
     * @since 2024-06-11
     */
    Optional<Authenticator> findByCredentialId(ByteArray credentialId);

    /**
     * 주어진 사용자로 모든 Authenticator를 조회합니다.
     *
     * @param user 사용자
     * @return List<Authenticator> 주어진 사용자로 조회된 모든 Authenticator
     * @since 2024-06-11
     */
    List<Authenticator> findAllByUser(AppUser user);

    /**
     * 주어진 사용자로 Authenticator를 조회합니다.
     *
     * @param user 사용자
     * @return Authenticator 주어진 사용자로 조회된 Authenticator
     * @since 2024-06-11
     */
    Authenticator findByUser(AppUser user);

    /**
     * 주어진 자격증명 ID로 모든 Authenticator를 조회합니다.
     *
     * @param credentialId 자격증명 ID
     * @return List<Authenticator> 주어진 자격증명 ID로 조회된 모든 Authenticator
     * @since 2024-06-11
     */
    List<Authenticator> findAllByCredentialId(ByteArray credentialId);

    void deleteAllByUser_Username(String username);
}
