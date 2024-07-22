package epams.domain.com.login.util.webauthn.user;

import epams.model.entity.BaseEntity;
import org.hibernate.annotations.Comment;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author K140024
 * @implNote 인사_외부근태 Webauthn사용자기본 엔티티 클래스
 * @since 2024-06-11
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "THURXE_CWUSRM")
@Comment("인사_외부근태 Webauthn사용자기본")
public class AppUser extends BaseEntity {

    /**
     * 직원번호
     * @author K140024
     * @implNote 직원번호 컬럼
     * @since 2024-06-11
     */
    @Id
    @Column(name = "ENO", nullable = false, unique = true, length = 32)
    @Comment("직원번호")
    private String username;

    /**
     * Webauthn 출력명
     * @author K140024
     * @implNote Webauthn 출력명 컬럼
     * @since 2024-06-11
     */
    @Column(name = "WEBA_PRO_NM", nullable = false, length = 32)
    @Comment("Webauthn 출력명")
    private String displayName;

    /**
     * Webauthn 사용자 Handle
     * @autor K140024
     * @implNote Webauthn 사용자 Handle 컬럼
     * @since 2024-06-11
     */
    @Lob
    @Column(name = "WEBA_USR_HDLN_IMG", nullable = false, length = 64)
    @Comment("Webauthn 사용자 Handle")
    private ByteArray handle;

    /**
     * AppUser 생성자
     * @autor K140024
     * @implNote AppUser 생성자
     * @since 2024-06-11
     */
    public AppUser(final String username, final String displayName, final ByteArray handle) {
        this.username = username;
        this.displayName = displayName;
        this.handle = handle;
    }

    /**
     * UserIdentity 객체로 변환
     * @return UserIdentity 객체
     * @autor K140024
     * @implNote UserIdentity 객체로 변환하는 메소드
     * @since 2024-06-11
     */
    public UserIdentity toUserIdentity() {
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }
}
