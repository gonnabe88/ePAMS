package epams.domain.com.login.util.webauthn.repository;

import com.yubico.webauthn.data.ByteArray;
import epams.domain.com.login.util.webauthn.user.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author K140024
 * @implNote AppUser에 대한 CRUD 리포지토리 인터페이스
 * @since 2024-06-11
 */
@Repository
public interface UserRepository extends CrudRepository<AppUser, String> {

    /**
     * 주어진 사용자 이름으로 AppUser를 조회합니다.
     * 
     * @param name 사용자 이름
     * @return AppUser 주어진 사용자 이름으로 조회된 AppUser
     * @since 2024-06-11
     */
    AppUser findByUsername(String name);

    /**
     * 주어진 핸들로 AppUser를 조회합니다.
     * 
     * @param handle ByteArray 핸들
     * @return AppUser 주어진 핸들로 조회된 AppUser
     * @since 2024-06-11
     */
    AppUser findByHandle(ByteArray handle);

    /**
     * 주어진 사용자 이름으로 AppUser를 삭제합니다.
     * @param username 사용자 이름
     * @since 2024-06-11
     */
    void deleteByUsername(String username);
}
