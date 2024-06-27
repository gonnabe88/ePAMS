package epams.com.login.util.webauthn.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.UserIdentity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "THURXE_CWUSRM")
@Comment("인사_외부근태 Webauthn사용자기본")
public class AppUser {
    
    @Id
    @Column(name = "ENO", nullable = false, unique = true, length = 32)
    @Comment("직원번호")
    private String username;

    @Column(name = "WEBAUTHN_DISP_NM", nullable = false, length = 32)
    @Comment("Webauthn 출력명")
    private String displayName;

    @Lob
    @Column(name = "WEBAUTHN_USR_HANDLE", nullable = false, length = 64)
    @Comment("Webauthn 사용자 Handle")
    private ByteArray handle;

    public AppUser(UserIdentity user) {
        this.handle = user.getId();
        this.username = user.getName();
        this.displayName = user.getDisplayName();
    }

    public UserIdentity toUserIdentity() {
        return UserIdentity.builder()
            .name(getUsername())
            .displayName(getDisplayName())
            .id(getHandle())
            .build();
    }
}